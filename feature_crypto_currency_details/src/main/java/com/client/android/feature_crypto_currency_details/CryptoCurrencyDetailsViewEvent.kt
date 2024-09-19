package com.client.android.feature_crypto_currency_details

import androidx.compose.runtime.Immutable
import com.client.android.common_utils.ErrorType
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel
import com.client.android.feature_base.Reducer

@Immutable
sealed class CryptoCurrencyDetailsViewEvent : Reducer.ViewEvent {
    data object GetCryptoCurrencyDetails : CryptoCurrencyDetailsViewEvent()
    data class OnCryptoCurrencyDetailsFetchedSuccess(val cryptoCurrencyModel: CryptoCurrencyModel) :
        CryptoCurrencyDetailsViewEvent()

    data class OnCryptoCurrencyDetailsFetchError(val error: ErrorType) :
        CryptoCurrencyDetailsViewEvent()
}
