package com.client.android.feature_crypto_currency_details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.client.android.common_ui.DisplayFullScreenError
import com.client.android.common_ui.components.AppProgressBar
import com.client.android.common_ui.components.AppText
import com.client.android.common_ui.typography
import com.client.android.common_utils.ErrorType
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel

@Composable
fun CryptoCurrencyDetailsScreen(
    navHostController: NavHostController,
    viewModel: CryptoCurrencyDetailsViewModel = hiltViewModel()
) {
    val viewState = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect

    LaunchedEffect(effect) {
        effect.collect {
            when (it) {
                is CryptoCurrencyDetailsViewEffect.OnCryptoCurrencyDetailsFetched -> {
                    // Side effect handling eg: success toast
                }
            }
        }
    }

    CryptoCurrencyDetailsInfo(
        loading = viewState.value.loading,
        error = viewState.value.error,
        cryptoCurrencyDetails = viewState.value.cryptoCurrencyModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CryptoCurrencyDetailsInfo(
    loading: Boolean,
    error: ErrorType?,
    cryptoCurrencyDetails: CryptoCurrencyModel?
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                AppText(
                    message = cryptoCurrencyDetails?.name
                        ?: stringResource(id = com.client.android.common_ui.R.string.crypto_currency_details_screen_title),
                    style = typography.headlineLarge
                )
            })
        }
    ) { paddingValues ->
        if (error != null) {
            DisplayFullScreenError(errorType = error)
        } else {
            if (loading) {
                AppProgressBar(modifier = Modifier.padding(paddingValues))
            } else {

            }
        }
    }
}
