plugins {
    id("com.android.library")
    id("base-convention")
}

android {
    namespace = "pro.yakuraion.englishhelper.commontests"
}

dependencies {
    implementation(libs.junit)
    implementation(libs.mockk)
    implementation(libs.kotlin.coroutines.test)
}
