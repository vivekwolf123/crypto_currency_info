package com.client.android.core_crypto_currency_data

import com.client.android.common_utils.ErrorType
import com.client.android.core_base.AppResult
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDetailsDataEntity
import com.client.android.core_crypto_currency_data.entity.CryptoCurrenciesDataEntity
import kotlinx.coroutines.flow.Flow

interface CryptoCurrencyDataRepository {

    suspend fun getTopCryptoCurrencies(): Flow<AppResult<CryptoCurrenciesDataEntity, ErrorType>>

    suspend fun getCryptoCurrencyDetails(cryptoCurrencyDetailsId: String): Flow<AppResult<CryptoCurrencyDetailsDataEntity, ErrorType>>
}
