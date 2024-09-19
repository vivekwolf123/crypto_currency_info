package com.client.android.core_crypto_currency_domain.model

data class CryptoCurrencyModel(
    val id: String,
    val symbol: String,
    val name: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val supply: String,
)
