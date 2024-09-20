plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.client.android.feature_base"
    buildFeatures {
        compose = true
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
}
