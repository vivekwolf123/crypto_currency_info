package com.client.android.feature_crypto_currency_details

import com.client.android.feature_base.Reducer

class CryptoCurrencyDetailsReducer :
    Reducer<CryptoCurrencyDetailsViewState, CryptoCurrencyDetailsViewEffect, CryptoCurrencyDetailsViewEvent> {

    override fun reduce(
        state: CryptoCurrencyDetailsViewState,
        event: CryptoCurrencyDetailsViewEvent
    ): Pair<CryptoCurrencyDetailsViewState, CryptoCurrencyDetailsViewEffect?> {
        return when (event) {
            is CryptoCurrencyDetailsViewEvent.GetCryptoCurrencyDetails -> {
                state.copy(loading = true) to null
            }

            is CryptoCurrencyDetailsViewEvent.OnCryptoCurrencyDetailsFetchedSuccess -> {
                state.copy(
                    loading = false,
                    cryptoCurrencyModel = event.cryptoCurrencyModel
                ) to CryptoCurrencyDetailsViewEffect.OnCryptoCurrencyDetailsFetched
            }

            is CryptoCurrencyDetailsViewEvent.OnCryptoCurrencyDetailsFetchError -> {
                state.copy(loading = false, error = event.error) to null
            }
        }
    }
}
