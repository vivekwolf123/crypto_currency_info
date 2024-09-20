package com.client.android.core_crypto_currency_domain

import com.client.android.common_utils.ErrorType
import com.client.android.core_base.AppResult
import com.client.android.core_crypto_currency_data.CryptoCurrencyDataRepository
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDataEntity
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDetailsDataEntity
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel
import com.client.android.core_crypto_currency_domain.usecase.FormatDecimalUseCase
import com.client.android.core_crypto_currency_domain.usecase.GetCryptoCurrencyDetailsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetCryptoCurrencyDetailsUseCaseTest {

    @Mock
    private lateinit var cryptoCurrencyDataRepository: CryptoCurrencyDataRepository

    @Mock
    private lateinit var formatDecimalUseCase: FormatDecimalUseCase

    private lateinit var getCryptoCurrencyDetailsUseCase: GetCryptoCurrencyDetailsUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getCryptoCurrencyDetailsUseCase = GetCryptoCurrencyDetailsUseCase(
            cryptoCurrencyDataRepository,
            formatDecimalUseCase
        )
    }

    @Test
    fun `given successful repository response when GetCryptoCurrencyDetailsUseCase is invoked then returns mapped crypto details`() =
        runTest {
            // Given
            val cryptoCurrencyDetailsDataEntity = CryptoCurrencyDetailsDataEntity(
                cryptoCurrencyInfoData = CryptoCurrencyDataEntity(
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
            `when`(cryptoCurrencyDataRepository.getCryptoCurrencyDetails("1")).thenReturn(
                flowOf(AppResult.Success(cryptoCurrencyDetailsDataEntity))
            )

            `when`(formatDecimalUseCase.invoke("50000.0")).thenReturn("50K")
            `when`(formatDecimalUseCase.invoke("18000000")).thenReturn("18M")
            `when`(formatDecimalUseCase.invoke("900000000")).thenReturn("900M")
            `when`(formatDecimalUseCase.invoke("10000000")).thenReturn("10M")

            // When
            val result = getCryptoCurrencyDetailsUseCase.invoke("1")

            // Then
            assert(result.first() is AppResult.Success)
            val expectedModel = CryptoCurrencyModel(
                id = "1",
                symbol = "BTC",
                name = "Bitcoin",
                priceUsd = "50K",
                changePercent24Hr = "5,47",
                supply = "18M",
                marketCapUsd = "900M",
                volumeUsd24Hr = "10M"
            )
            assertEquals(expectedModel, (result.first() as AppResult.Success).data)
        }

    @Test
    fun `given repository error when GetCryptoCurrencyDetailsUseCase is invoked then returns appropriate error result`() =
        runTest {
            // Given
            val errorType = AppResult.Error(ErrorType.SERVER_ERROR)
            `when`(cryptoCurrencyDataRepository.getCryptoCurrencyDetails("1")).thenReturn(
                flowOf(errorType)
            )

            // When
            val result = getCryptoCurrencyDetailsUseCase.invoke("1").first()

            // Then
            assertEquals(errorType, result)
        }
}

