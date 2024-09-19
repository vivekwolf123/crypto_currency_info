package com.client.android.feature_crypto_currency_details

import androidx.compose.runtime.Immutable
import com.client.android.feature_base.Reducer

@Immutable
sealed class CryptoCurrencyDetailsViewEffect : Reducer.ViewEffect {
    data object OnCryptoCurrencyDetailsFetched : CryptoCurrencyDetailsViewEffect()
}
