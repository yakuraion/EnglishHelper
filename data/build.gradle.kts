plugins {
    id("com.android.library")
    id("base-convention")
    id("data-uses-convention")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
}
