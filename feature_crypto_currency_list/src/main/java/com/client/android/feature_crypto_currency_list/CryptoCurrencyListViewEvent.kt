package com.client.android.feature_crypto_currency_list

import androidx.compose.runtime.Immutable
import com.client.android.common_utils.ErrorType
import com.client.android.core_crypto_currency_domain.model.CryptoCurrenciesModel
import com.client.android.feature_base.Reducer

@Immutable
sealed class CryptoCurrencyListViewEvent : Reducer.ViewEvent {
    data object GetTopCryptoCurrencyList : CryptoCurrencyListViewEvent()
    data class OnTopCryptoCurrencyListFetched(val cryptoCurrenciesModel: CryptoCurrenciesModel) :
        CryptoCurrencyListViewEvent()

    data class OnTopCryptoCurrencyListFetchError(val error: com.client.android.common_utils.ErrorType) :
        CryptoCurrencyListViewEvent()
}
