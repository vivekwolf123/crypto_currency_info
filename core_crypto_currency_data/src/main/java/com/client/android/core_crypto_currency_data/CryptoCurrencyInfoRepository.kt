package com.client.android.core_crypto_currency_data

import com.client.android.core_base.AppResult
import com.client.android.core_base.ErrorType
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyInfoDataEntity
import kotlinx.coroutines.flow.Flow

interface CryptoCurrencyInfoRepository {

    suspend fun getTopCryptoCurrencies(): Flow<AppResult<CryptoCurrencyInfoDataEntity, ErrorType>>
}
