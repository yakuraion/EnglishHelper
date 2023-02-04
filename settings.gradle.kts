pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "EnglishHelper"

include(":app")
include(":data")
include(":domain")
include(":common")
include(":common-ui")
include(":common-tests")
include(":common-tests-ui")
include(":features:startup")
include(":features:vocabulary")

includeBuild("plugins/conventions")
