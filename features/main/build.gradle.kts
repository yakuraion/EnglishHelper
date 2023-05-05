plugins {
    id("feature")
}

android {
    namespace = "pro.yakuraion.englishhelper.main"
}

dependencies {
    implementation(project(":features:startup"))
    implementation(project(":features:vocabulary"))
}
