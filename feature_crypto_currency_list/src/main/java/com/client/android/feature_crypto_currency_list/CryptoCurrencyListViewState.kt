package com.client.android.feature_crypto_currency_list

import androidx.compose.runtime.Immutable
import com.client.android.core_base.ErrorType
import com.client.android.core_crypto_currency_domain.model.CryptoCurrenciesInfoDataModel
import com.client.android.feature_base.Reducer

@Immutable
data class CryptoCurrencyListViewState(
    val loading: Boolean = false,
    val cryptoCurrenciesInfoDataModel: CryptoCurrenciesInfoDataModel? = null,
    val error: ErrorType? = null
) : Reducer.ViewState
