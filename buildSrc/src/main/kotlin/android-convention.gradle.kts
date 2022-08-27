import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.android")
    id("pro.yakuraion.plugins.codecheck.code-check")
}

configure<BaseExtension> {
    compileSdkVersion(33)

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    detektConfig.from(files("${configsDir}/detekt/detekt.yml"))
}

dependencies {
    add("implementation", versionCatalog.findLibrary("androidx-core-ktx").get())
    add("implementation", versionCatalog.findLibrary("androidx-appcompat").get())
    add("implementation", versionCatalog.findLibrary("material").get())

    add("testImplementation", versionCatalog.findLibrary("junit").get())
    add("androidTestImplementation", versionCatalog.findLibrary("androidx-junit").get())
    add("androidTestImplementation", versionCatalog.findLibrary("espresso").get())
}
