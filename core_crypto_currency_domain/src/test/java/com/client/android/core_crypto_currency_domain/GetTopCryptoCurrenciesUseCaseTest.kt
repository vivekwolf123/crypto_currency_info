package com.client.android.core_crypto_currency_domain

import com.client.android.common_utils.ErrorType
import com.client.android.core_base.AppResult
import com.client.android.core_crypto_currency_data.CryptoCurrencyDataRepository
import com.client.android.core_crypto_currency_data.entity.CryptoCurrenciesDataEntity
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDataEntity
import com.client.android.core_crypto_currency_domain.model.CryptoCurrenciesModel
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel
import com.client.android.core_crypto_currency_domain.usecase.FormatDecimalUseCase
import com.client.android.core_crypto_currency_domain.usecase.GetTopCryptoCurrenciesUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.Locale

class GetTopCryptoCurrenciesUseCaseTest {

    @Mock
    private lateinit var cryptoCurrencyDataRepository: CryptoCurrencyDataRepository

    @Mock
    private lateinit var formatDecimalUseCase: FormatDecimalUseCase

    private lateinit var getTopCryptoCurrenciesUseCase: GetTopCryptoCurrenciesUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getTopCryptoCurrenciesUseCase = GetTopCryptoCurrenciesUseCase(
            cryptoCurrencyDataRepository,
            formatDecimalUseCase
        )
        Locale.setDefault(Locale.US)
    }

    @Test
    fun `given successful repository response when GetTopCryptoCurrenciesUseCase is invoked then it returns mapped crypto currencies`() =
        runTest {
            // Given
            val mockEntity = CryptoCurrenciesDataEntity(
                data = listOf(
                    CryptoCurrencyDataEntity(
                        id = "1",
                        symbol = "BTC",
                        name = "Bitcoin",
                        priceUsd = "50000.0",
                        changePercent24Hr = "5.4669460918066241",
                        supply = "18000000",
                        marketCapUsd = "900000000",
                        volumeUsd24Hr = "10000000"
                    )
                )
            )

            `when`(cryptoCurrencyDataRepository.getTopCryptoCurrencies())
                .thenReturn(flowOf(AppResult.Success(mockEntity)))

            `when`(formatDecimalUseCase.invoke("50000.0")).thenReturn("50K")
            `when`(formatDecimalUseCase.invoke("900000000")).thenReturn("900M")
            `when`(formatDecimalUseCase.invoke("10000000")).thenReturn("10M")

            // When
            val result = getTopCryptoCurrenciesUseCase.invoke()

            // Then
            assert(result.first() is AppResult.Success)
            val expectedModel = CryptoCurrenciesModel(
                data = listOf(
                    CryptoCurrencyModel(
                        id = "1",
                        symbol = "BTC",
                        name = "Bitcoin",
                        priceUsd = "50K",
                        changePercent24Hr = "5.47",
                        supply = "18000000",
                        marketCapUsd = "900M",
                        volumeUsd24Hr = "10M"
                    )
                )
            )
            assertEquals(expectedModel, (result.first() as AppResult.Success).data)
        }

    @Test
    fun `given repository error when GetTopCryptoCurrenciesUseCase is invoked then returns appropriate error result`() =
        runTest {
            // Given
            val errorType = AppResult.Error(ErrorType.SERVER_ERROR)
            `when`(cryptoCurrencyDataRepository.getTopCryptoCurrencies()).thenReturn(
                flowOf(
                    errorType
                )
            )

            // When
            val result = getTopCryptoCurrenciesUseCase.invoke().first()

            // Then
            assertEquals(errorType, result)
        }
}
