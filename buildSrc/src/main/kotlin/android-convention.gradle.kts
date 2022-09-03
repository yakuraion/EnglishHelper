@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.BaseExtension
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

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.compose = true

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

    composeOptions.kotlinCompilerExtensionVersion = "1.3.0"
}

codeCheck {
    jvmTarget.set(JavaVersion.VERSION_11.toString())
    detektConfig.from(files("$configsDir/detekt/detekt.yml"))
}

dependencies {
    add("implementation", versionCatalog.findLibrary("dagger").get())
    add("kapt", versionCatalog.findLibrary("dagger-compiler").get())

    add("implementation", versionCatalog.findLibrary("androidx-core-ktx").get())
    add("implementation", versionCatalog.findLibrary("android-lifecycle").get())

    add("implementation", versionCatalog.findLibrary("compose-material").get())
    add("implementation", versionCatalog.findLibrary("compose-tooling-preview").get())

    add("implementation", versionCatalog.findLibrary("compose-activity").get())
    add("implementation", versionCatalog.findLibrary("compose-viewmodel").get())

    add("debugImplementation", versionCatalog.findLibrary("compose-tooling").get())

    add("debugImplementation", versionCatalog.findLibrary("custom-view").get())
    add("debugImplementation", versionCatalog.findLibrary("custom-view-pooling-container").get())

    add("testImplementation", versionCatalog.findLibrary("junit").get())

    add("androidTestImplementation", versionCatalog.findLibrary("androidx-junit").get())
    add("androidTestImplementation", versionCatalog.findLibrary("compose-junit").get())
    add("androidTestImplementation", versionCatalog.findLibrary("espresso").get())
}
