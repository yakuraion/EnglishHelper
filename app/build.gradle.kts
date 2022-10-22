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
        versionCode = 1
        versionName = "0.1.0"
    }

    val prodKeystorePropertiesFile = file(
        "${System.getProperty("user.home")}/keystores/EnglishHelper/credentials.properties"
    )
    val prodKeystoreProperties = Properties().apply { load(FileInputStream(prodKeystorePropertiesFile)) }
    signingConfigs {
        create("prod") {
            keyAlias = prodKeystoreProperties.getProperty("keyAlias")
            keyPassword = prodKeystoreProperties.getProperty("keyPassword")
            storeFile = file(prodKeystoreProperties.getProperty("keystorePath"))
            storePassword = prodKeystoreProperties.getProperty("keystorePassword")
        }
        create("dev") {
            keyAlias = "dev"
            keyPassword = "password"
            storeFile = rootProject.layout.projectDirectory.file("dev-keystore.jks").asFile
            storePassword = "password"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("prod")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("dev")
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":modules:vocabulary"))
}
