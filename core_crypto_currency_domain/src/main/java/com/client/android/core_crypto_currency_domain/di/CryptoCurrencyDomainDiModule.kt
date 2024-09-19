package com.client.android.core_crypto_currency_domain.di

import com.client.android.core_crypto_currency_data.CryptoCurrencyDataRepository
import com.client.android.core_crypto_currency_domain.usecase.FormatDecimalUseCase
import com.client.android.core_crypto_currency_domain.usecase.GetCryptoCurrencyDetailsUseCase
import com.client.android.core_crypto_currency_domain.usecase.GetTopCryptoCurrenciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object CryptoCurrencyDomainDiModule {

    @Provides
    @ViewModelScoped
    fun provideGetTopCryptoCurrenciesInfoUseCase(
        cryptoCurrencyDataRepository: CryptoCurrencyDataRepository,
        formatDecimalUseCase: FormatDecimalUseCase
    ) =
        GetTopCryptoCurrenciesUseCase(cryptoCurrencyDataRepository, formatDecimalUseCase)

    @Provides
    @ViewModelScoped
    fun provideGetCryptoCurrencyDetailsInfoUseCase(
        cryptoCurrencyDataRepository: CryptoCurrencyDataRepository,
        formatDecimalUseCase: FormatDecimalUseCase
    ) =
        GetCryptoCurrencyDetailsUseCase(cryptoCurrencyDataRepository, formatDecimalUseCase)

    @Provides
    @ViewModelScoped
    fun provideFormatDecimalUseCase() = FormatDecimalUseCase()
}
