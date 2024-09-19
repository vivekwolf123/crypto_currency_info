package com.client.android.navigation

sealed class AppRouter(val route: String) {
    data object CryptoCurrencyListScreen : AppRouter("CryptoCurrencyListScreen")
    data object CryptoCurrencyDetailsScreen : AppRouter("CryptoCurrencyDetailsScreen/{cryptoCurrencyDetailsId}") {
        fun navigateToCryptoCurrencyDetailsScreen(cryptoCurrencyDetailsId: String): String {
            return "CryptoCurrencyDetailsScreen/$cryptoCurrencyDetailsId"
        }
    }
}
