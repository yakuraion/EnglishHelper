tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
    subprojects.forEach { delete(it.buildDir) }
}
