package com.client.android.feature_crypto_currency_details

import com.client.android.feature_base.Reducer

data class CryptoCurrencyDetailsViewState(
    val loading: Boolean = false,

) : Reducer.ViewState
