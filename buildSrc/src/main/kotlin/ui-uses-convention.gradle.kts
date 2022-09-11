@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.BaseExtension

configure<BaseExtension> {

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = "1.3.0"
}

dependencies {
    add("implementation", versionCatalog.findLibrary("androidx-core-ktx").get())
    add("implementation", versionCatalog.findLibrary("android-lifecycle").get())

    add("implementation", versionCatalog.findLibrary("compose-material").get())
    add("implementation", versionCatalog.findLibrary("compose-tooling-preview").get())

    add("implementation", versionCatalog.findLibrary("compose-activity").get())
    add("implementation", versionCatalog.findLibrary("compose-viewmodel").get())

    add("debugImplementation", versionCatalog.findLibrary("compose-tooling").get())

    add("debugImplementation", versionCatalog.findLibrary("custom-view").get())
    add("debugImplementation", versionCatalog.findLibrary("custom-view-pooling-container").get())

    add("androidTestImplementation", versionCatalog.findLibrary("androidx-junit").get())
    add("androidTestImplementation", versionCatalog.findLibrary("compose-junit").get())
    add("androidTestImplementation", versionCatalog.findLibrary("espresso").get())
}
