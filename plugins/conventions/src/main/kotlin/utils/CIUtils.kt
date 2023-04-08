package utils

val isCI: Boolean
    get() = System.getenv("GITHUB_ACTIONS") == "true"
