@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.BaseExtension
import utils.versionCatalog

configure<BaseExtension> {

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "1.3.2"
}

dependencies {
    add("implementation", versionCatalog.findLibrary("androidx-core-ktx").get())
    add("implementation", versionCatalog.findLibrary("android-lifecycle").get())
    add("androidTestImplementation", versionCatalog.findLibrary("androidx-junit").get())

    add("implementation", versionCatalog.findLibrary("compose-material-icons-extended").get())
    add("implementation", versionCatalog.findLibrary("compose-material3").get())
    add("implementation", versionCatalog.findLibrary("compose-tooling-preview").get())
    add("implementation", versionCatalog.findLibrary("compose-constraint-layout").get())
    add("implementation", versionCatalog.findLibrary("compose-activity").get())
    add("implementation", versionCatalog.findLibrary("compose-viewmodel").get())
    add("debugImplementation", versionCatalog.findLibrary("compose-tooling").get())
    add("androidTestImplementation", versionCatalog.findLibrary("compose-junit").get())

    add("implementation", versionCatalog.findLibrary("accompanist-systemuicontroller").get())
    add("implementation", versionCatalog.findLibrary("accompanist-pager").get())

    add("implementation", versionCatalog.findLibrary("navigation-fragment").get())
    add("implementation", versionCatalog.findLibrary("navigation-ui").get())
    add("implementation", versionCatalog.findLibrary("navigation-compose").get())
    add("debugImplementation", versionCatalog.findLibrary("navigation-testing").get())

    add("debugImplementation", versionCatalog.findLibrary("custom-view").get())
    add("debugImplementation", versionCatalog.findLibrary("custom-view-pooling-container").get())

    add("androidTestImplementation", versionCatalog.findLibrary("espresso").get())
}
