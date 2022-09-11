plugins {
    id("com.android.library")
    id("base-convention")
    id("ui-uses-convention")
}

android {
    resourcePrefix(name + '_')
}

dependencies {
    add("implementation", project(":common"))
    add("implementation", project(":domain"))
}
