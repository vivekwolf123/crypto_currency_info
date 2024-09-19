package com.client.android.core_crypto_currency_data.cache

import com.google.gson.reflect.TypeToken
import androidx.room.TypeConverter
import com.client.android.core_crypto_currency_data.entity.CryptoCurrenciesDataEntity
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDataEntity
import com.google.gson.Gson

internal class Converters {
    @TypeConverter
    fun fromCryptoCurrencyInfoDataEntity(cryptoCurrencyDataEntity: CryptoCurrencyDataEntity?): String? {
        if (cryptoCurrencyDataEntity == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<CryptoCurrenciesDataEntity>() {}.type
        return gson.toJson(cryptoCurrencyDataEntity, type)
    }

    @TypeConverter
    fun toCryptoCurrencyInfoDataEntity(cryptoCurrencyDataEntityString: String?): CryptoCurrenciesDataEntity? {
        if (cryptoCurrencyDataEntityString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<CryptoCurrenciesDataEntity>() {}.type
        return gson.fromJson(cryptoCurrencyDataEntityString, type)
    }
}
