package com.client.android.core_crypto_currency_data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "crypto_currency_info_data_table")
data class CryptoCurrencyDataEntity(
    @PrimaryKey
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

    @SerializedName("supply")
    val supply: String,
)
