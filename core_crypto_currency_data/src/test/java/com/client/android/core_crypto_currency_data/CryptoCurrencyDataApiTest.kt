package com.client.android.core_crypto_currency_data

import com.client.android.core_crypto_currency_data.api.CryptoCurrencyDataApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.OkHttpClient
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoCurrencyDataApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: CryptoCurrencyDataApi

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val client = OkHttpClient.Builder().build()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoCurrencyDataApi::class.java)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `given successful response when fetchTopCryptoCurrenciesInfo API is called then returns expected data`() = runBlocking {
        // Given
        val mockResponseBody = """
            {
                "data": [
                    {
                        "id": "bitcoin",
                        "symbol": "BTC",
                        "name": "Bitcoin",
                        "priceUsd": "50000.0"
                    }
                ]
            }
        """
        mockWebServer.enqueue(MockResponse().setBody(mockResponseBody).setResponseCode(200))

        // When
        val response = api.fetchTopCryptoCurrenciesInfo(10)

        // Then
        Assertions.assertEquals(200, response.code())
        Assertions.assertEquals("bitcoin", response.body()?.data?.get(0)?.id)
        Assertions.assertEquals("Bitcoin", response.body()?.data?.get(0)?.name)
        Assertions.assertEquals("BTC", response.body()?.data?.get(0)?.symbol)
        Assertions.assertEquals("50000.0", response.body()?.data?.get(0)?.priceUsd)
    }

    @Test
    fun `given successful response when fetchCryptoCurrencyDetailsInfo API is called then returns expected data`() = runBlocking {
        // Given
        val mockResponseBody = """
            {
                "data": {
                    "id": "bitcoin",
                    "symbol": "BTC",
                    "name": "Bitcoin",
                    "priceUsd": "50000.0",
                    "marketCapUsd": "1000000000",
                    "volumeUsd24Hr": "10000000"
                }
            }
        """
        mockWebServer.enqueue(MockResponse().setBody(mockResponseBody).setResponseCode(200))

        // When
        val response = api.fetchCryptoCurrencyDetailsInfo("bitcoin")

        // Then
        Assertions.assertEquals(200, response.code())
        Assertions.assertEquals("bitcoin", response.body()?.cryptoCurrencyInfoData?.id)
        Assertions.assertEquals("Bitcoin", response.body()?.cryptoCurrencyInfoData?.name)
        Assertions.assertEquals("BTC", response.body()?.cryptoCurrencyInfoData?.symbol)
        Assertions.assertEquals("50000.0", response.body()?.cryptoCurrencyInfoData?.priceUsd)
        Assertions.assertEquals("1000000000", response.body()?.cryptoCurrencyInfoData?.marketCapUsd)
        Assertions.assertEquals("10000000", response.body()?.cryptoCurrencyInfoData?.volumeUsd24Hr)
    }

    @Test
    fun `given error response when fetchTopCryptoCurrenciesInfo API is called then returns error`() = runBlocking {
        // Arrange
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        // Act
        val response = api.fetchTopCryptoCurrenciesInfo(10)

        // Assert
        Assertions.assertEquals(500, response.code())
    }

    @Test
    fun `given error response when fetchCryptoCurrencyDetailsInfo API is called then returns error`() = runBlocking {
        // Arrange
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        // Act
        val response = api.fetchCryptoCurrencyDetailsInfo("1")

        // Assert
        Assertions.assertEquals(500, response.code())
    }
}
