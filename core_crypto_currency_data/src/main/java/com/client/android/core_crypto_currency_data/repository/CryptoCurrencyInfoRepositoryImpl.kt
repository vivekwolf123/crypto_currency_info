package com.client.android.core_crypto_currency_data.repository

import android.content.Context
import com.client.android.core_base.AppResult
import com.client.android.core_base.ErrorType
import com.client.android.core_base.ErrorType.Companion.handleErrorCode
import com.client.android.core_crypto_currency_data.CryptoCurrencyInfoRepository
import com.client.android.core_crypto_currency_data.api.CryptoCurrencyInfoApi
import com.client.core_base.isInternetAvailableOnPhone
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class CryptoCurrencyInfoRepositoryImpl @Inject constructor(
    private val context: Context,
    private val cryptoCurrencyInfoApi: CryptoCurrencyInfoApi
) : CryptoCurrencyInfoRepository {

    private val topCryptoCurrenciesCount = 10
    private val apiCallDelayMillis = 60000L

    override suspend fun getTopCryptoCurrencies() = flow {
        repeat(Int.MAX_VALUE) {
            if (context.isInternetAvailableOnPhone()) {
                val response = cryptoCurrencyInfoApi.fetchTopCryptoCurrenciesInfo(topCryptoCurrenciesCount)
                if (response.isSuccessful) {
                    response.body()?.let { cryptoCurrencyInfo ->
                        emit(AppResult.Success(cryptoCurrencyInfo))
                    } ?: emit(AppResult.Error(ErrorType.CRYPTO_CURRENCY_INFO_LIST_ERROR))
                } else {
                    emit(AppResult.Error(handleErrorCode(response.code())))
                }
            } else {
                emit(AppResult.Error(ErrorType.NO_INTERNET))
            }
            delay(apiCallDelayMillis)
        }
    }.catch {
        emit(AppResult.Error(ErrorType.UNKNOWN_ERROR))
    }
}
