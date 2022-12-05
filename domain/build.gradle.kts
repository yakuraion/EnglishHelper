plugins {
    id("com.android.library")
    id("base-convention")
}

android {
    namespace = "pro.yakuraion.englishhelper.domain"

    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "1.3.2"
}

dependencies {
    implementation(project(":common"))
    testImplementation(project(":common-tests"))

    implementation(libs.compose.runtime)
}
