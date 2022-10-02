import utils.versionCatalog

dependencies {
    add("implementation", versionCatalog.findLibrary("room-runtime").get())
    add("implementation", versionCatalog.findLibrary("room-ktx").get())
    add("kapt", versionCatalog.findLibrary("room-compiler").get())

    add("implementation", versionCatalog.findLibrary("volley").get())
}
