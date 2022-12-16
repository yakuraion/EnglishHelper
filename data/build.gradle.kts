plugins {
    id("com.android.library")
    id("base-convention")
    id("data-uses-convention")
}

android {
    namespace = "pro.yakuraion.englishhelper.data"
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(libs.gson)
    implementation(libs.jsoup)
}
