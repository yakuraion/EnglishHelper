plugins {
    id("application-convention")
}

android {
    defaultConfig {
        applicationId = "pro.yakuraion.englishhelper"
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
}

dependencies {
    implementation(project(":modules:vocabulary"))
}
