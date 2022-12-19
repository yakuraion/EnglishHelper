plugins {
    id("com.android.library")
    id("base-convention")
    id("data-uses-convention")
}

android {
    namespace = "pro.yakuraion.englishhelper.data"

    val roomSchemaLocation = "$projectDir/schemas/room"

    defaultConfig {
        kapt {
            arguments {
                arg("room.schemaLocation", roomSchemaLocation)
            }
        }
    }

    sourceSets {
        get("androidTest").assets.srcDir(files(roomSchemaLocation))
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(libs.gson)
    implementation(libs.jsoup)
}
