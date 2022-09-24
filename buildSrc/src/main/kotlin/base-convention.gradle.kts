import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin-android")
    id("kotlin-kapt")
    id("pro.yakuraion.plugins.codecheck.code-check")
}

configure<BaseExtension> {
    compileSdkVersion(32)

    defaultConfig {
        minSdk = 24
        targetSdk = 32
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType(KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
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
