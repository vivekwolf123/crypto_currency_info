package com.client.android.feature_crypto_currency_details

import com.client.android.common_utils.ErrorType
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

class CryptoCurrencyDetailsReducerTest {
    private lateinit var reducer: CryptoCurrencyDetailsReducer

    @BeforeEach
    fun setUp() {
        reducer = CryptoCurrencyDetailsReducer()
    }

    @Test
    fun `given CryptoCurrencyDetailsReducer when event is GetCryptoCurrencyDetails then return loading true`() {
        // Given
        val initialState = CryptoCurrencyDetailsViewState()

        // When
        val (newState, effect) = reducer.reduce(
            initialState,
            CryptoCurrencyDetailsViewEvent.GetCryptoCurrencyDetails
        )

        // Then
        val expectedState = initialState.copy(loading = true, error = null)
        Assertions.assertEquals(expectedState, newState)
        Assertions.assertEquals(null, effect)
    }

    @Test
    fun `given CryptoCurrencyDetailsReducer when event is OnCryptoCurrencyDetailsFetchedSuccess then return crypto currency model`() {
        // Given
        val initialState = CryptoCurrencyDetailsViewState(loading = true)
        val cryptoCurrencyDetailsModel = CryptoCurrencyModel(
            id = "1",
            symbol = "BTC",
            name = "Bitcoin",
            priceUsd = "50K",
            changePercent24Hr = "5,47",
            supply = "18M",
            marketCapUsd = "900M",
            volumeUsd24Hr = "10M"
        )

        // When
        val (newState, effect) = reducer.reduce(
            initialState,
            CryptoCurrencyDetailsViewEvent.OnCryptoCurrencyDetailsFetchedSuccess(
                cryptoCurrencyDetailsModel
            )
        )

        // Then
        val expectedState = initialState.copy(
            loading = false,
            cryptoCurrencyModel = cryptoCurrencyDetailsModel,
            error = null
        )
        Assertions.assertEquals(expectedState, newState)
        Assertions.assertEquals(
            CryptoCurrencyDetailsViewEffect.OnCryptoCurrencyDetailsFetched,
            effect
        )
    }

    @Test
    fun `given CryptoCurrencyDetailsReducer when event is OnCryptoCurrencyDetailsFetchError then return appropriate error type`() {
        // Given
        val initialState = CryptoCurrencyDetailsViewState(loading = true)
        val errorType = ErrorType.UNKNOWN_ERROR

        // When
        val (newState, effect) = reducer.reduce(
            initialState,
            CryptoCurrencyDetailsViewEvent.OnCryptoCurrencyDetailsFetchError(errorType)
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
