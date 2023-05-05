import utils.getPropertiesOrNull

plugins {
    id("com.android.application")
    id("common")
    id("common-ui")
}

val customProperties = getPropertiesOrNull("custom.properties")

android {
    namespace = "pro.yakuraion.englishhelper"

    defaultConfig {
        applicationId = "pro.yakuraion.englishhelper"
        versionCode = 6
        versionName = "0.6.0"

        buildConfigField(
            "String", "NETWORK_WORDS_HOST",
            "\"${customProperties?.get("network.words.host")}\""
        )
        buildConfigField(
            "String", "NETWORK_WORDS_KEY",
            "\"${customProperties?.get("network.words.key")}\""
        )
    }

    signingConfigs {
        val releaseKeystoreProperties = getPropertiesOrNull(
            "${System.getProperty("user.home")}/keystores/EnglishHelper/credentialds.properties"
        )
        if (releaseKeystoreProperties != null) {
            create("release") {
                keyAlias = releaseKeystoreProperties.getProperty("keyAlias")
                keyPassword = releaseKeystoreProperties.getProperty("keyPassword")
                storeFile = file(releaseKeystoreProperties.getProperty("keystorePath"))
                storePassword = releaseKeystoreProperties.getProperty("keystorePassword")
            }
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.findByName("release")

            resValue("string", "app_name", "EnglishHelper")
        }
        getByName("debug") {
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
    implementation(project(":common-ui"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":features:main"))
}
