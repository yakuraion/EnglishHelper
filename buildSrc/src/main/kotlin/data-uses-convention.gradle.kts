import org.gradle.kotlin.dsl.dependencies

dependencies {
    add("implementation", versionCatalog.findLibrary("room-runtime").get())
    add("implementation", versionCatalog.findLibrary("room-ktx").get())
    add("kapt", versionCatalog.findLibrary("room-compiler").get())
}