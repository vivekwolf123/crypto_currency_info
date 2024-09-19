plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.client.android.crypto_currency_info_app"
    hilt {
        enableAggregatingTask = false
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.material)
    implementation(libs.androidx.activity.compose)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(project(":common_ui"))
    implementation(project(":navigation"))
    implementation(project(":core_crypto_currency_data"))
    implementation(project(":core_crypto_currency_domain"))
    implementation(project(":core_base"))
    implementation(project(":feature_base"))
    implementation(project(":feature_crypto_currency_list"))
    implementation(project(":feature_crypto_currency_details"))
}