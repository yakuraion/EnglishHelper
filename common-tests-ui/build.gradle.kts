import utils.versionCatalog

plugins {
    id("com.android.library")
    id("base-convention")
}

android {
    namespace = "pro.yakuraion.englishhelper.commontestsui"
}

dependencies {
    val composeBom = platform(versionCatalog.findLibrary("compose-bom").get())
    implementation(composeBom)

    implementation(libs.compose.junit)
}
