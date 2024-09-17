package com.client.android.feature_crypto_currency_list

import androidx.compose.runtime.Immutable
import com.client.android.feature_base.Reducer

@Immutable
sealed class CryptoCurrencyListViewEffect : Reducer.ViewEffect {
    data object OnTopCryptoCurrencyListFetched : CryptoCurrencyListViewEffect()
}
