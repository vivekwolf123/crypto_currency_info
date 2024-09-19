package com.client.android.core_crypto_currency_data.api

import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDetailsDataEntity
import com.client.android.core_crypto_currency_data.entity.CryptoCurrenciesDataEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoCurrencyDataApi {

    @GET("assets")
    suspend fun fetchTopCryptoCurrenciesInfo(@Query("limit") limit: Int): Response<CryptoCurrenciesDataEntity>

    @GET("assets/{id}")
    suspend fun fetchCryptoCurrencyDetailsInfo(@Path("id") id: String): Response<CryptoCurrencyDetailsDataEntity>
}
