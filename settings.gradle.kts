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
include(":modules:vocabulary")

includeBuild("plugins/code-check") {
    dependencySubstitution {
        substitute(module("pro.yakuraion.plugins.codecheck:code-check")).using(project(":"))
    }
}
