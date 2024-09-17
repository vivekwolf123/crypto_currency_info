plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.client.android.navigation"
}

dependencies {
    api(libs.retrofit.converter.gson)
    api(libs.androidx.navigation.compose)
}
