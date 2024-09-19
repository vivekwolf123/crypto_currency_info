package com.client.android.common_ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.client.android.common_ui.components.AppText
import com.client.android.common_utils.ErrorType

@Composable
fun DisplayFullScreenError(errorType: ErrorType) {
    when (errorType) {
        ErrorType.NO_INTERNET -> {
            AppText(
                message = stringResource(id = com.client.android.common_ui.R.string.no_internet),
                style = typography.bodyLarge,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        ErrorType.CRYPTO_CURRENCY_INFO_LIST_EMPTY_ERROR -> {
            AppText(
                message = stringResource(id = com.client.android.common_ui.R.string.crypto_currency_list_empty),
                style = typography.bodyLarge,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        ErrorType.CRYPTO_CURRENCY_INFO_DETAILS_ERROR -> {
            AppText(
                message = stringResource(id = com.client.android.common_ui.R.string.crypto_currency_list_empty),
                style = typography.bodyLarge,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        else -> {
            AppText(
                message = errorType.name,
                style = typography.bodyLarge,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}