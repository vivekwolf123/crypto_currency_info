package com.client.android.core_crypto_currency_domain.di

import com.client.android.core_crypto_currency_data.CryptoCurrencyInfoRepository
import com.client.android.core_crypto_currency_domain.usecase.FormatDecimalUseCase
import com.client.android.core_crypto_currency_domain.usecase.GetTopCryptoCurrenciesInfoUseCase
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
        cryptoCurrencyInfoRepository: CryptoCurrencyInfoRepository,
        formatDecimalUseCase: FormatDecimalUseCase
    ) =
        GetTopCryptoCurrenciesInfoUseCase(cryptoCurrencyInfoRepository, formatDecimalUseCase)

    @Provides
    @ViewModelScoped
    fun provideFormatDecimalUseCase() = FormatDecimalUseCase()
}
