package com.client.android.feature_crypto_currency_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.client.android.common_ui.DisplayFullScreenError
import com.client.android.common_ui.components.AppProgressBar
import com.client.android.common_ui.components.AppText
import com.client.android.common_ui.typography
import com.client.android.common_utils.ErrorType
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel
import com.client.android.navigation.AppRouter

@Composable
fun CryptoCurrencyListScreen(
    navHostController: NavHostController,
    viewModel: CryptoCurrencyListViewModel = hiltViewModel()
) {
    val viewState = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect

    LaunchedEffect(effect) {
        effect.collect {
            when (it) {
                is CryptoCurrencyListViewEffect.OnTopCryptoCurrencyListFetched -> {
                    // Side effect handling eg: success toast
                }
            }
        }
    }

    CryptoCurrencyList(
        loading = viewState.value.loading,
        error = viewState.value.error,
        topCryptoCurrencyInfoList = viewState.value.cryptoCurrenciesModel?.data
            ?: emptyList(),
        onItemClicked = { item ->
            navHostController.navigate(
                AppRouter.CryptoCurrencyDetailsScreen.navigateToCryptoCurrencyDetailsScreen(
                    item.id
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CryptoCurrencyList(
    loading: Boolean,
    error: ErrorType?,
    topCryptoCurrencyInfoList: List<CryptoCurrencyModel>,
    onItemClicked: (CryptoCurrencyModel) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                AppText(
                    message = stringResource(id = com.client.android.common_ui.R.string.crypto_currency_list_screen_title),
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
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    items(topCryptoCurrencyInfoList, key = { item -> item.id }) { item ->
                        CryptoCurrencyListItem(
                            name = item.name,
                            symbol = item.symbol,
                            price = "$" + item.priceUsd,
                            changePercent = item.changePercent24Hr + "%",
                            changeColor = if (item.changePercent24Hr.startsWith("-")) Color.Red else Color.Green,
                            onItemClicked = onItemClicked
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CryptoCurrencyListItem(
    name: String,
    symbol: String,
    price: String,
    changePercent: String,
    changeColor: Color,
    onItemClicked: (CryptoCurrencyModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFE8EAF6), RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Spacer(modifier = Modifier.width(16.dp))

        // Name and Symbol
        Column(modifier = Modifier.weight(1f)) {
            AppText(
                message = name,
                style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            AppText(
                message = symbol,
                style = typography.bodyMedium,
                color = Color.Gray
            )
        }

        // Price and Change Percentage
        Column(horizontalAlignment = Alignment.End) {
            AppText(
                message = price,
                style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            AppText(
                message = changePercent,
                style = typography.bodyMedium,
                color = changeColor
            )
        }
    }
}
