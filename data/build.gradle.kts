plugins {
    id("com.android.library")
    id("common")
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
        get("androidTest").assets.srcDir(roomSchemaLocation)
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.gson)
    implementation(libs.jsoup)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    androidTestImplementation(libs.room.testing)

    implementation(libs.volley)

    implementation(libs.yakuraion.android.common.network)
}
