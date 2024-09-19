package com.client.android.common_utils

import java.util.Locale

fun String.roundChangePercent24HrToTwoDecimalPlaces(): String {
    return try {
        String.format(Locale.getDefault(), "%.2f", this.toDoubleOrNull())
    } catch (_: Exception) {
        this
    }
}
