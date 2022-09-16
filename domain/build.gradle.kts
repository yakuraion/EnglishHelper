plugins {
    id("com.android.library")
    id("base-convention")
}

dependencies {
    implementation(project(":common"))
    testImplementation(project(":common-tests"))
}
