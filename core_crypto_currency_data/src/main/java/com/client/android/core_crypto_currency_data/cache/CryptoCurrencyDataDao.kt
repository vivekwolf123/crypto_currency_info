package com.client.android.core_crypto_currency_data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDataEntity

@Dao
interface CryptoCurrencyDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeCryptoCurrencyInfoDataInCache(cryptoCurrencyData: List<CryptoCurrencyDataEntity>)

    @Query("SELECT * FROM crypto_currency_info_data_table WHERE id = :id")
    suspend fun getCryptoCurrencyInfoDataFromCache(id: String): CryptoCurrencyDataEntity?
}
