package utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import java.io.File
import java.io.FileInputStream
import java.util.*

val Project.versionCatalog
    get() = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

val Project.configsDir: File
    get() = file("$rootDir/configs")

fun Project.getPropertiesOrNull(path: String): Properties? {
    val file = file(path)
    return if (file.exists()) {
        val stream = FileInputStream(file)
        Properties().apply { load(stream) }
    } else {
        null
    }
}
