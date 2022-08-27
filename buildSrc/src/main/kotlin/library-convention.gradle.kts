plugins {
    id("com.android.library")
    id("android-convention")
}

android {
    resourcePrefix(name + '_')
}
