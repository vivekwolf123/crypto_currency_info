package com.client.android.feature_crypto_currency_list

import com.client.android.feature_base.Reducer

class CryptoCurrencyListReducer :
    Reducer<CryptoCurrencyListViewState, CryptoCurrencyListViewEffect, CryptoCurrencyListViewEvent> {

    override fun reduce(
        state: CryptoCurrencyListViewState,
        event: CryptoCurrencyListViewEvent
    ): Pair<CryptoCurrencyListViewState, CryptoCurrencyListViewEffect?> {
        return when (event) {
            is CryptoCurrencyListViewEvent.GetTopCryptoCurrencyList -> {
                state.copy(loading = true) to null
            }

            is CryptoCurrencyListViewEvent.OnTopCryptoCurrencyListFetched -> {
                state.copy(
                    loading = false,
                    cryptoCurrenciesInfoDataModel = event.cryptoCurrenciesInfoDataModel
                ) to CryptoCurrencyListViewEffect.OnTopCryptoCurrencyListFetched
            }

            is CryptoCurrencyListViewEvent.OnTopCryptoCurrencyListFetchError -> {
                state.copy(loading = false, error = event.error) to null
            }
        }
    }
}
