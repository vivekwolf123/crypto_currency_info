package com.client.android.feature_crypto_currency_list

import com.client.android.common_utils.ErrorType
import com.client.android.core_crypto_currency_domain.model.CryptoCurrenciesModel
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CryptoCurrencyListReducerTest {
    private lateinit var reducer: CryptoCurrencyListReducer

    @BeforeEach
    fun setUp() {
        reducer = CryptoCurrencyListReducer()
    }

    @Test
    fun `given CryptoCurrencyListReducer when event is GetTopCryptoCurrencyList then return loading true`() {
        // Given
        val initialState = CryptoCurrencyListViewState()

        // When
        val (newState, effect) = reducer.reduce(
            initialState,
            CryptoCurrencyListViewEvent.GetTopCryptoCurrencyList
        )

        // Then
        val expectedState = initialState.copy(loading = true, error = null)
        Assertions.assertEquals(expectedState, newState)
        Assertions.assertEquals(null, effect)
    }

    @Test
    fun `given CryptoCurrencyListReducer when event is OnTopCryptoCurrencyListFetchSuccess then return crypto currencies model`() {
        // Given
        val initialState = CryptoCurrencyListViewState(loading = true)
        val cryptoCurrenciesModel = CryptoCurrenciesModel(
            listOf(
                CryptoCurrencyModel(
                    id = "1",
                    symbol = "BTC",
                    name = "Bitcoin",
                    priceUsd = "50K",
                    changePercent24Hr = "5,47",
                    supply = "18M",
                    marketCapUsd = "900M",
                    volumeUsd24Hr = "10M"
                )
            )
        )

        // When
        val (newState, effect) = reducer.reduce(
            initialState,
            CryptoCurrencyListViewEvent.OnTopCryptoCurrencyListFetchSuccess(
                cryptoCurrenciesModel
            )
        )

        // Then
        val expectedState = initialState.copy(
            loading = false,
            cryptoCurrenciesModel = cryptoCurrenciesModel,
            error = null
        )
        Assertions.assertEquals(expectedState, newState)
        Assertions.assertEquals(
            CryptoCurrencyListViewEffect.OnTopCryptoCurrencyListFetched,
            effect
        )
    }

    @Test
    fun `given CryptoCurrencyListReducer when event is OnTopCryptoCurrencyListFetchError then return appropriate error type`() {
        // Given
        val initialState = CryptoCurrencyListViewState(loading = true)
        val errorType = ErrorType.UNKNOWN_ERROR

        // When
        val (newState, effect) = reducer.reduce(
            initialState,
            CryptoCurrencyListViewEvent.OnTopCryptoCurrencyListFetchError(errorType)
        )

        // Then
        val expectedState = initialState.copy(
            loading = false,
            error = errorType
        )
        Assertions.assertEquals(expectedState, newState)
        Assertions.assertEquals(null, effect)
    }
}
