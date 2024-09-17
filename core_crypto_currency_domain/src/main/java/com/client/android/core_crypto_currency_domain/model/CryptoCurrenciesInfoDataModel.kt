package com.client.android.core_crypto_currency_domain.model

data class CryptoCurrenciesInfoDataModel(
    val data: List<InfoDataModel>
)

data class InfoDataModel(
    val id: String,
    val symbol: String,
    val name: String,
    val priceUsd: String,
    val changePercent24Hr: String,
)
