plugins {
    id("com.android.library")
    id("base-convention")
}

android {
    namespace = "pro.yakuraion.englishhelper.domain"
}

dependencies {
    implementation(project(":common"))
    testImplementation(project(":common-tests"))
}
