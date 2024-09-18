package com.client.android.core_crypto_currency_domain.usecase

import com.client.android.core_base.AppResult
import com.client.android.core_base.ErrorType
import com.client.android.core_crypto_currency_data.CryptoCurrencyInfoRepository
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyInfoDataEntity
import com.client.android.core_crypto_currency_domain.model.CryptoCurrenciesInfoDataModel
import com.client.android.core_crypto_currency_domain.model.InfoDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class GetTopCryptoCurrenciesInfoUseCase @Inject constructor(
    private val cryptoCurrencyInfoRepository: CryptoCurrencyInfoRepository,
    private val formatDecimalUseCase: FormatDecimalUseCase
) {
    suspend operator fun invoke(): Flow<AppResult<CryptoCurrenciesInfoDataModel, ErrorType>> {
        return cryptoCurrencyInfoRepository.getTopCryptoCurrencies().map { result ->
            when (result) {
                is AppResult.Success -> {
                    AppResult.Success(
                        mapCryptoCurrenciesInfoDataEntityToCryptoCurrenciesInfoDataModel(result.data)
                    )
                }

                is AppResult.Error -> {
                    AppResult.Error(result.error)
                }
            }
        }
    }

    private fun mapCryptoCurrenciesInfoDataEntityToCryptoCurrenciesInfoDataModel(
        cryptoCurrencyInfoDataEntity: CryptoCurrencyInfoDataEntity
    ): CryptoCurrenciesInfoDataModel {
        return CryptoCurrenciesInfoDataModel(
            data = cryptoCurrencyInfoDataEntity.data.map { data ->
                InfoDataModel(
                    id = data.id,
                    symbol = data.symbol,
                    name = data.name,
                    priceUsd = formatDecimalUseCase.invoke(data.priceUsd),
                    changePercent24Hr = roundChangePercent24HrToTwoDecimalPlaces(data.changePercent24Hr)
                )
            }
        )
    }

    private fun roundChangePercent24HrToTwoDecimalPlaces(input: String): String {
        return try {
            String.format(Locale.getDefault(), "%.2f", input.toDoubleOrNull())
        } catch (_: Exception) {
            input
        }
    }
}
