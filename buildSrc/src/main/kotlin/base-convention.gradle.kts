import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import utils.configsDir
import utils.enableComposeCompilerMetrics
import utils.enableComposeCompilerReports
import utils.optIn
import utils.versionCatalog

plugins {
    id("kotlin-android")
    id("kotlin-kapt")
    id("pro.yakuraion.plugins.codecheck.code-check")
}

configure<BaseExtension> {
    compileSdkVersion(33)

    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType(KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()

            optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")

            if (project.findProperty("composeCompilerReports") == "true") {
                enableComposeCompilerReports(project.buildDir.absolutePath + "/compose_compiler")
            }
            if (project.findProperty("composeCompilerMetrics") == "true") {
                enableComposeCompilerMetrics(project.buildDir.absolutePath + "/compose_compiler")
            }
        }
    }
}

configure<KotlinAndroidProjectExtension> {
    sourceSets.all {
        languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
    }
}

codeCheck {
    jvmTarget.set(JavaVersion.VERSION_11.toString())
    detektConfig.from(files("$configsDir/detekt/detekt.yml"))
}

dependencies {
    add("implementation", versionCatalog.findLibrary("kotlin-coroutines").get())
    add("testImplementation", versionCatalog.findLibrary("kotlin-coroutines-test").get())

    add("implementation", versionCatalog.findLibrary("dagger").get())
    add("kapt", versionCatalog.findLibrary("dagger-compiler").get())

    add("implementation", versionCatalog.findLibrary("timber").get())

    add("testImplementation", versionCatalog.findLibrary("junit").get())
    add("testImplementation", versionCatalog.findLibrary("mockk").get())
}
