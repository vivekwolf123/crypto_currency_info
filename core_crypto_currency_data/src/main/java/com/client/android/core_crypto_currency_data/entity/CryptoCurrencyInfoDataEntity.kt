package com.client.android.core_crypto_currency_data.entity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoCurrencyInfoDataEntity(

    @SerializedName("data")
    val data: List<Data>
)

@Serializable
data class Data(
    @SerializedName("id")
    val id: String,

    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("priceUsd")
    val priceUsd: String,

    @SerializedName("changePercent24Hr")
    val changePercent24Hr: String,
)
