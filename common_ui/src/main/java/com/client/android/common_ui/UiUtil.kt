package com.client.android.common_ui

import androidx.compose.ui.graphics.Color

fun getCryptoCurrencyImage(id: String): Int {
    return when (id) {
        "bitcoin" -> R.drawable.ic_bitcoin
        "ethereum" -> R.drawable.ic_ethereum
        "tether" -> R.drawable.ic_tether
        "binance-coin" -> R.drawable.ic_bnb
        "xrp" -> R.drawable.ic_xrp
        "polygon" -> R.drawable.ic_polygon
        "avalanche" -> R.drawable.ic_avalanche
        "cardano" -> R.drawable.ic_cardano
        else -> R.drawable.ic_default_crypto_currency
    }
}

fun getChange24HourPercentColor(changePercent24Hr: String?): Color {
    return if ((changePercent24Hr?.toDoubleOrNull()
            ?: 0.0) >= 0
    ) Green else Red
}
