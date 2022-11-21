plugins {
    id("com.android.library")
    id("base-convention")
    id("ui-uses-convention")
}

android {
    namespace = "pro.yakuraion.englishhelper.commonui"
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
}
