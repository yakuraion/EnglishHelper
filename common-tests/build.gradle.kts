plugins {
    id("com.android.library")
    id("base-convention")
}

dependencies {
    implementation(libs.junit)
    implementation(libs.mockk)
    implementation(libs.kotlin.coroutines.test)
}
