package com.client.android.core_crypto_currency_data.repository

import android.content.Context
import com.client.android.common_utils.ErrorType
import com.client.android.core_base.AppResult
import com.client.android.common_utils.ErrorType.Companion.handleErrorCode
import com.client.android.core_crypto_currency_data.CryptoCurrencyDataRepository
import com.client.android.core_crypto_currency_data.api.CryptoCurrencyDataApi
import com.client.android.core_crypto_currency_data.cache.CryptoCurrencyDataDao
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDetailsDataEntity
import com.client.core_base.isInternetAvailableOnPhone
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class CryptoCurrencyDataRepositoryImpl @Inject constructor(
    private val context: Context,
    private val cryptoCurrencyDataApi: CryptoCurrencyDataApi,
    private val cryptoCurrencyDataDao: CryptoCurrencyDataDao
) : CryptoCurrencyDataRepository {

    private val topCryptoCurrenciesCount = 10
    private val apiCallDelayMillis = 60000L

    override suspend fun getTopCryptoCurrencies() = flow {
        repeat(Int.MAX_VALUE) {
            if (context.isInternetAvailableOnPhone()) {
                val response =
                    cryptoCurrencyDataApi.fetchTopCryptoCurrenciesInfo(topCryptoCurrenciesCount)
                if (response.isSuccessful) {
                    response.body()?.let { cryptoCurrencyInfo ->
                        cryptoCurrencyDataDao.storeCryptoCurrencyInfoDataInCache(cryptoCurrencyInfo.data)
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

    override suspend fun getCryptoCurrencyDetails(cryptoCurrencyDetailsId: String) = flow {
        if (context.isInternetAvailableOnPhone()) {
            val cryptoCurrencyInfoDetailsFromCache = cryptoCurrencyDataDao.getCryptoCurrencyInfoDataFromCache(cryptoCurrencyDetailsId)
            if (cryptoCurrencyInfoDetailsFromCache != null) {
                emit(AppResult.Success(CryptoCurrencyDetailsDataEntity(cryptoCurrencyInfoDetailsFromCache)))
            }
            val response =
                cryptoCurrencyDataApi.fetchCryptoCurrencyDetailsInfo(cryptoCurrencyDetailsId)
            if (response.isSuccessful) {
                response.body()?.let { cryptoCurrencyDetailsInfo ->
                    emit(AppResult.Success(cryptoCurrencyDetailsInfo))
                } ?: emit(AppResult.Error(ErrorType.CRYPTO_CURRENCY_INFO_DETAILS_ERROR))
            } else {
                emit(AppResult.Error(handleErrorCode(response.code())))
            }
        } else {
            emit(AppResult.Error(ErrorType.NO_INTERNET))
        }
    }.catch {
        emit(AppResult.Error(ErrorType.UNKNOWN_ERROR))
    }
}
