import utils.versionCatalog

plugins {
    id("com.android.library")
    id("common")
    id("common-ui")
}

android {
    namespace = "pro.yakuraion.englishhelper.commontestsui"
}

dependencies {
    val composeBom = platform(versionCatalog.findLibrary("compose-bom").get())
    implementation(composeBom)
    implementation(libs.compose.junit)

    implementation(libs.bundles.androidx.test)

    implementation(project(":common-ui"))
}
