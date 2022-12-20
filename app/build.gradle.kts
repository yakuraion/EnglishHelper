import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("base-convention")
    id("ui-uses-convention")
    id("data-uses-convention")
}

val customProperties = Properties()
customProperties.load(FileInputStream(rootProject.file("custom.properties")))

android {
    namespace = "pro.yakuraion.englishhelper"

    defaultConfig {
        applicationId = "pro.yakuraion.englishhelper"
        versionCode = 4
        versionName = "0.4.0"

        buildConfigField("String", "NETWORK_WORDS_HOST", "\"${customProperties["network.words.host"]}\"")
        buildConfigField("String", "NETWORK_WORDS_KEY", "\"${customProperties["network.words.key"]}\"")
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

    applicationVariants.all {
        outputs.forEach { output ->
            if (output is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
                output.outputFileName = "$applicationId-v$versionName.${output.outputFile.extension}"
            }
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":common-ui"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":features:startup"))
    implementation(project(":features:vocabulary"))
}
