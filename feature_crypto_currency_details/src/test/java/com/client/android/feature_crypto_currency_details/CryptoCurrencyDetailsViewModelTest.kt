package com.client.android.feature_crypto_currency_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.client.android.common_utils.ErrorType
import com.client.android.core_base.AppResult
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel
import com.client.android.core_crypto_currency_domain.usecase.GetCryptoCurrencyDetailsUseCase
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
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class CryptoCurrencyDetailsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val getCryptoCurrencyDetailsUseCase: GetCryptoCurrencyDetailsUseCase = mockk()

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: CryptoCurrencyDetailsViewModel

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given valid cryptoCurrencyId when fetching details then success event is triggered`() =
        runTest {
            // Given
            val successResult = AppResult.Success(mockCryptoCurrencyModel())
            coEvery { getCryptoCurrencyDetailsUseCase.invoke("bitcoin") } returns flow {
                emit(successResult)
            }

            // When
            viewModel =
                CryptoCurrencyDetailsViewModel(getCryptoCurrencyDetailsUseCase, savedStateHandle)

            // Then
            val eventJob = launch {
                viewModel.event.toList().apply {
                    Assertions.assertEquals(2, size)
                    Assertions.assertEquals(
                        CryptoCurrencyDetailsViewEvent.GetCryptoCurrencyDetails,
                        this[0]
                    )
                    Assertions.assertEquals(
                        CryptoCurrencyDetailsViewEvent.OnCryptoCurrencyDetailsFetchedSuccess(
                            mockCryptoCurrencyModel()
                        ), this[1]
                    )
                }
            }

            advanceUntilIdle()
            eventJob.cancelAndJoin()
        }

    @Test
    fun `given valid cryptoCurrencyId when fetching details then error event is triggered`() =
        runTest {
            // Given
            val errorResult = AppResult.Error(ErrorType.UNKNOWN_ERROR)
            coEvery { getCryptoCurrencyDetailsUseCase.invoke("bitcoin") } returns flow {
                emit(errorResult)
            }

            // When
            viewModel =
                CryptoCurrencyDetailsViewModel(getCryptoCurrencyDetailsUseCase, savedStateHandle)

            // Then
            val eventJob = launch {
                viewModel.event.toList().apply {
                    Assertions.assertEquals(2, size)
                    Assertions.assertEquals(
                        CryptoCurrencyDetailsViewEvent.GetCryptoCurrencyDetails,
                        this[0]
                    )
                    Assertions.assertEquals(
                        CryptoCurrencyDetailsViewEvent.OnCryptoCurrencyDetailsFetchError(
                            errorResult.error
                        ), this[1]
                    )
                }
            }

            advanceUntilIdle()
            eventJob.cancelAndJoin()
        }

    private fun mockCryptoCurrencyModel(): CryptoCurrencyModel {
        return CryptoCurrencyModel(
            id = "1",
            symbol = "BTC",
            name = "Bitcoin",
            priceUsd = "50K",
            changePercent24Hr = "5,47",
            supply = "18000000",
            marketCapUsd = "900M",
            volumeUsd24Hr = "10M"
        )
    }
}
