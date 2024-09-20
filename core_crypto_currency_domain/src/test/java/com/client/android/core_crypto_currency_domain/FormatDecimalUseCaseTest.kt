package com.client.android.core_crypto_currency_domain

import com.client.android.core_crypto_currency_domain.usecase.FormatDecimalUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Locale

class FormatDecimalUseCaseTest {

    private lateinit var formatDecimalUseCase: FormatDecimalUseCase

    @BeforeEach
    fun setUp() {
        formatDecimalUseCase = FormatDecimalUseCase()
        Locale.setDefault(Locale.US)
    }

    @Test
    fun `given value less than thousand when FormatDecimalUseCase invoked then return formatted value with one decimal place`() {
        val result = formatDecimalUseCase.invoke("999.567")
        assertEquals("999.6", result)
    }

    @Test
    fun `given value in thousands when FormatDecimalUseCase invoked then return K suffix`() {
        val result = formatDecimalUseCase.invoke("1500")
        assertEquals("1.5K", result)
    }

    @Test
    fun `given value in millions when FormatDecimalUseCase invoked then return M suffix`() {
        val result = formatDecimalUseCase.invoke("1500000")
        assertEquals("1.5M", result)
    }

    @Test
    fun `given value in billions when FormatDecimalUseCase invoked then return B suffix`() {
        val result = formatDecimalUseCase.invoke("1500000000")
        assertEquals("1.5B", result)
    }

    @Test
    fun `given invalid input when FormatDecimalUseCase invoked then return default value`() {
        val result = formatDecimalUseCase.invoke("invalid")
        assertEquals("0.0", result)
    }

    @Test
    fun `given empty input when FormatDecimalUseCase invoked then return default value`() {
        val result = formatDecimalUseCase.invoke("")
        assertEquals("0.0", result)
    }

    @Test
    fun `given large value with default locale when FormatDecimalUseCase invoked then return formatted value with correct suffix`() {
        val result = formatDecimalUseCase.invoke("1234567890")
        assertEquals("1.2B", result)
    }

    @Test
    fun `given value in trillions when FormatDecimalUseCase invoked then return T suffix`() {
        val result = formatDecimalUseCase.invoke("1500000000000")
        assertEquals("1.5T", result)
    }
}
