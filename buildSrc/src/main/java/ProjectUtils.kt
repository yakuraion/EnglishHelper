import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import java.io.File

val Project.versionCatalog
    get() = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

val Project.configsDir: File
    get() = file("$rootDir/configs")
