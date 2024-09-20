package com.client.android.feature_crypto_currency_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.client.android.common_utils.ErrorType
import com.client.android.core_base.AppResult
import com.client.android.core_crypto_currency_domain.model.CryptoCurrenciesModel
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel
import com.client.android.core_crypto_currency_domain.usecase.GetTopCryptoCurrenciesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class CryptoCurrencyListViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val getTopCryptoCurrenciesUseCase: GetTopCryptoCurrenciesUseCase = mockk()

    private lateinit var viewModel: CryptoCurrencyListViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given top cryptocurrencies available when fetching top crypto currencies then success event is triggered with data`() =
        runTest {
            // Given
            val successResult = AppResult.Success(mockCryptoCurrenciesModel())
            coEvery { getTopCryptoCurrenciesUseCase.invoke() } returns flow {
                emit(successResult)
            }

            // When
            viewModel =
                CryptoCurrencyListViewModel(getTopCryptoCurrenciesUseCase)

            // Then
            val eventJob = launch {
                viewModel.event.toList().apply {
                    Assertions.assertEquals(2, size)
                    Assertions.assertEquals(
                        CryptoCurrencyListViewEvent.GetTopCryptoCurrencyList,
                        this[0]
                    )
                    Assertions.assertEquals(
                        CryptoCurrencyListViewEvent.OnTopCryptoCurrencyListFetchSuccess(
                            mockCryptoCurrenciesModel()
                        ), this[1]
                    )
                }
            }

            advanceUntilIdle()
            eventJob.cancelAndJoin()
        }

    @Test
    fun `given top cryptocurrencies not available when fetching top crypto currencies then error event is triggered`() =
        runTest {
            // Given
            val errorResult = AppResult.Error(ErrorType.SERVER_ERROR)
            coEvery { getTopCryptoCurrenciesUseCase.invoke() } returns flow {
                emit(errorResult)
            }

            // When
            viewModel =
                CryptoCurrencyListViewModel(getTopCryptoCurrenciesUseCase)

            // Then
            val eventJob = launch {
                viewModel.event.toList().apply {
                    Assertions.assertEquals(2, size)
                    Assertions.assertEquals(
                        CryptoCurrencyListViewEvent.GetTopCryptoCurrencyList,
                        this[0]
                    )
                    Assertions.assertEquals(
                        CryptoCurrencyListViewEvent.OnTopCryptoCurrencyListFetchError(
                            errorResult.error
                        ), this[1]
                    )
                }
            }

            advanceUntilIdle()
            eventJob.cancelAndJoin()
        }

    private fun mockCryptoCurrenciesModel(): CryptoCurrenciesModel {
        return CryptoCurrenciesModel(
            listOf(
                CryptoCurrencyModel(
                    id = "1",
                    symbol = "BTC",
                    name = "Bitcoin",
                    priceUsd = "50K",
                    changePercent24Hr = "5,47",
                    supply = "18000000",
                    marketCapUsd = "900M",
                    volumeUsd24Hr = "10M"
                )
            )
        )
    }
}
