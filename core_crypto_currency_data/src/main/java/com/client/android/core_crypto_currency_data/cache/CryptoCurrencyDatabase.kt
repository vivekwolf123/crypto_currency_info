package com.client.android.core_crypto_currency_data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDataEntity

@Database(entities = [CryptoCurrencyDataEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class CryptoCurrencyDatabase : RoomDatabase() {
    abstract fun cryptoCurrencyDataDao(): CryptoCurrencyDataDao
}
