package utils

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun KotlinJvmOptions.enableComposeCompilerReports(destination: String) {
    freeCompilerArgs = freeCompilerArgs + listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$destination"
    )
}

fun KotlinJvmOptions.enableComposeCompilerMetrics(destination: String) {
    freeCompilerArgs = freeCompilerArgs + listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$destination"
    )
}
