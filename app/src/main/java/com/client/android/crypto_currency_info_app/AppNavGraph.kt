package com.client.android.crypto_currency_info_app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.client.android.feature_crypto_currency_details.CryptoCurrencyDetailsScreen
import com.client.android.feature_crypto_currency_list.CryptoCurrencyListScreen
import com.client.android.navigation.AppRouter

@Composable
fun AppNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = AppRouter.CryptoCurrencyListScreen.route
    ) {
        composable(route = AppRouter.CryptoCurrencyListScreen.route) {
            CryptoCurrencyListScreen(navHostController)
        }
        composable(
            route = AppRouter.CryptoCurrencyDetailsScreen.route,
            arguments = listOf(navArgument("cryptoCurrencyDetailsId") { type = NavType.StringType })
        ) {
            CryptoCurrencyDetailsScreen(navHostController)
        }
    }
}
