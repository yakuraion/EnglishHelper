plugins {
    id("com.android.application")
    id("base-convention")
    id("ui-uses-convention")
    id("data-uses-convention")
}

android {
    defaultConfig {
        applicationId = "pro.yakuraion.englishhelper"
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":modules:vocabulary"))
}
