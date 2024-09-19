package com.client.android.feature_crypto_currency_details

import androidx.compose.runtime.Immutable
import com.client.android.common_utils.ErrorType
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel
import com.client.android.feature_base.Reducer

@Immutable
data class CryptoCurrencyDetailsViewState(
    val loading: Boolean = false,
    val cryptoCurrencyModel: CryptoCurrencyModel? = null,
    val error: com.client.android.common_utils.ErrorType? = null
) : Reducer.ViewState
