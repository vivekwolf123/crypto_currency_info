package com.client.android.common_utils

enum class ErrorType(val code: Int) {
    MALFORMED_REQUEST(400),
    SERVER_ERROR(500),
    NO_INTERNET(-1),
    UNKNOWN_ERROR(-2),
    CRYPTO_CURRENCY_INFO_LIST_ERROR(-3),
    CRYPTO_CURRENCY_INFO_LIST_EMPTY_ERROR(-4),
    CRYPTO_CURRENCY_INFO_DETAILS_ERROR(-5),
    CRYPTO_CURRENCY_INFO_DETAILS_MISSING_ERROR(-6);

    companion object {
        private val types = entries.associateBy { it.code }

        fun handleErrorCode(value: Int): ErrorType {
            types[value]?.let {
                return it
            }
            return UNKNOWN_ERROR
        }
    }
}
