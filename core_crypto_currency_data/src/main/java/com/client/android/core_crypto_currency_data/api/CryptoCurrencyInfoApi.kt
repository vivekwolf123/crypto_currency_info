package com.client.android.core_crypto_currency_data.api

import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyInfoDataEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoCurrencyInfoApi {

    @GET("assets")
    suspend fun fetchTopCryptoCurrenciesInfo(@Query("limit") limit: Int): Response<CryptoCurrencyInfoDataEntity>
}
