import utils.versionCatalog

plugins {
    id("com.android.library")
    id("base-convention")
    id("ui-uses-convention")
}

android {
    namespace = "pro.yakuraion.englishhelper.commontestsui"
}

dependencies {
    val composeBom = platform(versionCatalog.findLibrary("compose-bom").get())
    implementation(composeBom)

    implementation(libs.androidx.test.core)
    implementation(libs.androidx.test.core.ktx)
    implementation(libs.androidx.test.junit)
    implementation(libs.androidx.test.junit.ktx)
    implementation(libs.androidx.test.runner)

    implementation(libs.compose.junit)

    implementation(project(":common-ui"))
}
