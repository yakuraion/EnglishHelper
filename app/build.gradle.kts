import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("base-convention")
    id("ui-uses-convention")
    id("data-uses-convention")
}

android {
    namespace = "pro.yakuraion.englishhelper"

    defaultConfig {
        applicationId = "pro.yakuraion.englishhelper"
        versionCode = 3
        versionName = "0.3.0"
    }

    val releaseKeystorePropertiesFile = file(
        "${System.getProperty("user.home")}/keystores/EnglishHelper/credentials.properties"
    )
    val releaseKeystoreProperties = Properties().apply { load(FileInputStream(releaseKeystorePropertiesFile)) }
    signingConfigs {
        create("release") {
            keyAlias = releaseKeystoreProperties.getProperty("keyAlias")
            keyPassword = releaseKeystoreProperties.getProperty("keyPassword")
            storeFile = file(releaseKeystoreProperties.getProperty("keystorePath"))
            storePassword = releaseKeystoreProperties.getProperty("keystorePassword")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")

            resValue("string", "app_name", "EnglishHelper")
        }
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"

            resValue("string", "app_name", "EnglishHelper (debug)")
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":common-ui"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":features:vocabulary"))
}
