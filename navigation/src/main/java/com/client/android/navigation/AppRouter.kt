package com.client.android.navigation

sealed class AppRouter(val route: String) {
    data object CryptoCurrencyListScreen : AppRouter("CryptoCurrencyListScreen")
    data object CryptoCurrencyDetailsScreen : AppRouter("CryptoCurrencyDetailsScreen/{id}") {
        fun navigateToCryptoCurrencyDetailsScreen(id: String): String {
            return "CryptoCurrencyDetailsScreen/$id"
        }
    }
}
