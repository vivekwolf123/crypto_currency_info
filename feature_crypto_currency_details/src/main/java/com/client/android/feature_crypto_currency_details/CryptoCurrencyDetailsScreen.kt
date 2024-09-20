package com.client.android.feature_crypto_currency_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.client.android.common_ui.Blue
import com.client.android.common_ui.Blue30
import com.client.android.common_ui.GreyishBlack
import com.client.android.common_ui.Lavender
import com.client.android.common_ui.White40
import com.client.android.common_ui.components.DisplayFullScreenError
import com.client.android.common_ui.components.AppProgressBar
import com.client.android.common_ui.components.AppText
import com.client.android.common_ui.getChange24HourPercentColor
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
        cryptoCurrencyDetails = viewState.value.cryptoCurrencyModel,
        navHostController = navHostController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CryptoCurrencyDetailsInfo(
    loading: Boolean,
    error: ErrorType?,
    cryptoCurrencyDetails: CryptoCurrencyModel?,
    navHostController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                AppText(
                    message = cryptoCurrencyDetails?.name?.uppercase()
                        ?: stringResource(id = com.client.android.common_ui.R.string.crypto_currency_details_screen_title),
                    style = typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue30
                ),
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                })
        },
        containerColor = Lavender,
    ) { paddingValues ->
        if (error != null) {
            DisplayFullScreenError(errorType = error)
        } else {
            if (loading) {
                AppProgressBar(modifier = Modifier.padding(paddingValues))
            } else {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .padding(16.dp)
                        .background(
                            White40,
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    // Price
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppText(
                            message = stringResource(id = com.client.android.common_ui.R.string.price_label),
                            style = typography.bodyMedium,
                            color = GreyishBlack
                        )
                        AppText(
                            message = "$" + cryptoCurrencyDetails?.priceUsd,
                            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = GreyishBlack
                        )
                    }

                    // Price Change
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppText(
                            message = stringResource(id = com.client.android.common_ui.R.string.price_change_label_24_hour),
                            style = typography.bodyMedium,
                            color = GreyishBlack
                        )
                        AppText(
                            message = cryptoCurrencyDetails?.changePercent24Hr + "%",
                            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color =  getChange24HourPercentColor(cryptoCurrencyDetails?.changePercent24Hr)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(color = Blue)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Market Cap
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppText(
                            message = stringResource(id = com.client.android.common_ui.R.string.market_cap_label),
                            style = typography.bodyMedium,
                            color = GreyishBlack
                        )
                        AppText(
                            message = "$" + cryptoCurrencyDetails?.marketCapUsd,
                            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = GreyishBlack
                        )
                    }

                    // Volume (24hr)
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppText(
                            message = stringResource(id = com.client.android.common_ui.R.string.volume_label_24_hour),
                            style = typography.bodyMedium,
                            color = GreyishBlack
                        )
                        AppText(
                            message = "$" + cryptoCurrencyDetails?.volumeUsd24Hr,
                            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = GreyishBlack
                        )
                    }

                    // Supply
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppText(
                            message = stringResource(id = com.client.android.common_ui.R.string.supply_label),
                            style = typography.bodyMedium,
                            color = GreyishBlack
                        )
                        AppText(
                            message = cryptoCurrencyDetails?.supply,
                            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = GreyishBlack
                        )
                    }
                }
            }
        }
    }
}
