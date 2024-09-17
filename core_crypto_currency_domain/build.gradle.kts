plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.client.android.core_crypto_currency_domain"
    hilt {
        enableAggregatingTask = false
    }
}

dependencies {

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    api(project(":core_base"))
    implementation(project(":core_crypto_currency_data"))
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
}
