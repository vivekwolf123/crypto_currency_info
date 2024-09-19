plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.client.android.common_ui"
    buildFeatures {
        compose = true
    }
}

dependencies {

    api(libs.compose.material3)
    api(libs.compose.ui)

    implementation(project(":common_utils"))
}
