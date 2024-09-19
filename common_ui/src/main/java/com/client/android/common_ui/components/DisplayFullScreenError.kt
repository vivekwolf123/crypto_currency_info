package com.client.android.common_ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.client.android.common_ui.R
import com.client.android.common_ui.typography
import com.client.android.common_utils.ErrorType

@Composable
fun DisplayFullScreenError(errorType: ErrorType) {
    when (errorType) {
        ErrorType.NO_INTERNET -> {
            AppText(
                message = stringResource(id = R.string.no_internet),
                style = typography.bodyLarge,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        ErrorType.CRYPTO_CURRENCY_INFO_LIST_ERROR -> {
            AppText(
                message = stringResource(id = R.string.crypto_currency_list_error),
                style = typography.bodyLarge,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        ErrorType.CRYPTO_CURRENCY_INFO_LIST_EMPTY_ERROR -> {
            AppText(
                message = stringResource(id = R.string.crypto_currency_list_empty),
                style = typography.bodyLarge,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        ErrorType.CRYPTO_CURRENCY_INFO_DETAILS_ERROR,
        ErrorType.CRYPTO_CURRENCY_INFO_DETAILS_MISSING_ERROR -> {
            AppText(
                message = stringResource(id = R.string.crypto_currency_details_error),
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