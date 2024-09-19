package com.client.android.feature_crypto_currency_list

import androidx.compose.runtime.Immutable
import com.client.android.common_utils.ErrorType
import com.client.android.core_crypto_currency_domain.model.CryptoCurrenciesModel
import com.client.android.feature_base.Reducer

@Immutable
data class CryptoCurrencyListViewState(
    val loading: Boolean = false,
    val cryptoCurrenciesModel: CryptoCurrenciesModel? = null,
    val error: com.client.android.common_utils.ErrorType? = null
) : Reducer.ViewState
