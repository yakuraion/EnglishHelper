plugins {
    id("com.android.library")
    id("base-convention")
}

android {
    namespace = "pro.yakuraion.englishhelper.domain"

    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "1.4.0"
}

dependencies {
    testImplementation(project(":common-tests"))

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.compose.runtime)

    implementation(libs.gson)
}
