plugins {
    id("com.android.library")
    id("common")
    id("common-ui")
}

android {
    namespace = "pro.yakuraion.englishhelper.commonui"
}

dependencies {
    implementation(project(":domain"))
}
