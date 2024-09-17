package com.client.android.core_crypto_currency_domain.usecase

import java.util.Locale
import kotlin.math.ln
import kotlin.math.pow

class FormatDecimalUseCase {

    operator fun invoke(value: String): String {
        try {
            val numericValue = value.toDoubleOrNull()
            if (numericValue == null || numericValue < 1000) return String.format(
                Locale.getDefault(),
                "%.1f",
                numericValue ?: 0.0
            )

            val exp = (ln(numericValue) / ln(1000.0)).toInt()
            val suffixes = arrayOf("", "K", "M", "B", "T", "P", "E")

            return String.format(
                Locale.getDefault(),
                "%.1f%s",
                numericValue / 1000.0.pow(exp.toDouble()),
                suffixes[exp]
            )
        } catch (_: Exception) {
           return value
        }
    }
}
