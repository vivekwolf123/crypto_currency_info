package com.client.android.core_crypto_currency_data.entity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoCurrencyDetailsDataEntity(

    @SerializedName("data")
    val cryptoCurrencyInfoData: CryptoCurrencyDataEntity
)
