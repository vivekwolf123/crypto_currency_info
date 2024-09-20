package com.client.android.core_crypto_currency_data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.client.android.common_utils.ErrorType
import com.client.android.core_base.AppResult
import com.client.android.core_crypto_currency_data.api.CryptoCurrencyDataApi
import com.client.android.core_crypto_currency_data.cache.CryptoCurrencyDataDao
import com.client.android.core_crypto_currency_data.entity.CryptoCurrenciesDataEntity
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDataEntity
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDetailsDataEntity
import com.client.android.core_crypto_currency_data.repository.CryptoCurrencyDataRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import retrofit2.Response

@ExperimentalCoroutinesApi
class CryptoCurrencyDataRepositoryImplTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var cryptoCurrencyDataApi: CryptoCurrencyDataApi

    @Mock
    private lateinit var cryptoCurrencyDataDao: CryptoCurrencyDataDao

    @Mock
    private lateinit var networkInfo: NetworkInfo

    @Mock
    private lateinit var connectivityManager: ConnectivityManager

    private lateinit var repository: CryptoCurrencyDataRepository

    private val topCryptoCurrenciesCount = 10

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository =
            CryptoCurrencyDataRepositoryImpl(context, cryptoCurrencyDataApi, cryptoCurrencyDataDao)
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(
            connectivityManager
        )
    }

    @Test
    fun `given internet is available when cryptoCurrencyDataApi is called and returns success then CryptoCurrencyDataRepository returns success data`() =
        runTest {
            `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)

            val mockResponse = Response.success(mock(CryptoCurrenciesDataEntity::class.java))
            `when`(cryptoCurrencyDataApi.fetchTopCryptoCurrenciesInfo(topCryptoCurrenciesCount)).thenReturn(
                mockResponse
            )

            val result = repository.getTopCryptoCurrencies().first()

            assertTrue(result is AppResult.Success)
            verify(cryptoCurrencyDataDao).storeCryptoCurrencyInfoDataInCache(any())
        }

    @Test
    fun `given no internet when getTopCryptoCurrencies in CryptoCurrencyDataRepository is called then CryptoCurrencyDataRepository returns no internet`() = runTest {
        `when`(connectivityManager.activeNetworkInfo).thenReturn(null)

        val result = repository.getTopCryptoCurrencies().first()

        assertTrue(result is AppResult.Error)
        assertTrue((result as AppResult.Error).error == ErrorType.NO_INTERNET)
    }

    @Test
    fun `given internet is available when fetchTopCryptoCurrenciesInfo is called and returns 500 error then CryptoCurrencyDataRepository returns appropriate error type`() = runTest {
        `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)

        val mockErrorResponse = Response.error<CryptoCurrenciesDataEntity>(500, mock())
        `when`(cryptoCurrencyDataApi.fetchTopCryptoCurrenciesInfo(topCryptoCurrenciesCount)).thenReturn(
            mockErrorResponse
        )

        val result = repository.getTopCryptoCurrencies().first()

        assertTrue(result is AppResult.Error)
        assertTrue((result as AppResult.Error).error == ErrorType.SERVER_ERROR)
    }

    @Test
    fun `given internet is available when getCryptoCurrencyDetails in CryptoCurrencyDataRepository is called then CryptoCurrencyDataRepository returns details data from cache`() =
        runTest {
            `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)

            val cachedData = mock(CryptoCurrencyDataEntity::class.java)
            `when`(cryptoCurrencyDataDao.getCryptoCurrencyInfoDataFromCache(anyString())).thenReturn(
                cachedData
            )

            val mockApiResponse =
                Response.success(mock(CryptoCurrencyDetailsDataEntity::class.java))
            `when`(cryptoCurrencyDataApi.fetchCryptoCurrencyDetailsInfo(anyString())).thenReturn(
                mockApiResponse
            )

            val result = repository.getCryptoCurrencyDetails("bitcoin").first()

            assertTrue(result is AppResult.Success)
            verify(cryptoCurrencyDataDao).getCryptoCurrencyInfoDataFromCache(anyString())
        }

    @Test
    fun `given no internet when getCryptoCurrencyDetails in CryptoCurrencyDataRepository is called then CryptoCurrencyDataRepository returns no internet`() = runTest {
        `when`(connectivityManager.activeNetworkInfo).thenReturn(null)

        val result = repository.getCryptoCurrencyDetails("bitcoin").first()

        assertTrue(result is AppResult.Error)
        assertEquals(ErrorType.NO_INTERNET, (result as AppResult.Error).error)
    }

    @Test
    fun `given internet is available when fetchCryptoCurrencyDetailsInfo is called and returns 500 error then CryptoCurrencyDataRepository returns appropriate error type`() = runTest {
        `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)

        `when`(cryptoCurrencyDataDao.getCryptoCurrencyInfoDataFromCache(anyString())).thenReturn(
            null
        )
        val mockErrorResponse = Response.error<CryptoCurrencyDetailsDataEntity>(500, mock())
        `when`(cryptoCurrencyDataApi.fetchCryptoCurrencyDetailsInfo(anyString())).thenReturn(
            mockErrorResponse
        )

        val result = repository.getCryptoCurrencyDetails("bitcoin").first()

        assertTrue(result is AppResult.Error)
        assertTrue((result as AppResult.Error).error == ErrorType.SERVER_ERROR)
    }

    @Test
    fun `given internet is available when getTopCryptoCurrencies returns unknown error then CryptoCurrencyDataRepository returns appropriate error type`() = runTest {
        `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)

        `when`(cryptoCurrencyDataApi.fetchTopCryptoCurrenciesInfo(anyInt())).thenThrow(
            RuntimeException("Unknown error")
        )

        val result = repository.getTopCryptoCurrencies().first()

        assertTrue(result is AppResult.Error)
        assertTrue((result as AppResult.Error).error == ErrorType.UNKNOWN_ERROR)
    }

    @Test
    fun `given internet is available when getCryptoCurrencyDetails returns unknown error then CryptoCurrencyDataRepository returns appropriate error type`() = runTest {
        `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)

        `when`(cryptoCurrencyDataApi.fetchCryptoCurrencyDetailsInfo(anyString())).thenThrow(
            RuntimeException("Unknown error")
        )

        val result = repository.getCryptoCurrencyDetails("bitcoin").first()

        assertTrue(result is AppResult.Error)
        assertTrue((result as AppResult.Error).error == ErrorType.UNKNOWN_ERROR)
    }
}
