plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.application.plugin)
    implementation(libs.android.library.plugin)
    implementation(libs.kotlin.plugin)
    implementation(libs.plugin.code.check)
}
