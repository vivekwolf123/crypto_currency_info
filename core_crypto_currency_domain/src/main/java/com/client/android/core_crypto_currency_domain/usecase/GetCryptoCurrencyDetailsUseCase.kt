package com.client.android.core_crypto_currency_domain.usecase

import com.client.android.core_base.AppResult
import com.client.android.core_base.ErrorType
import com.client.android.core_crypto_currency_data.CryptoCurrencyDataRepository
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDetailsDataEntity
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class GetCryptoCurrencyDetailsUseCase @Inject constructor(
    private val cryptoCurrencyDataRepository: CryptoCurrencyDataRepository,
    private val formatDecimalUseCase: FormatDecimalUseCase
) {
    suspend operator fun invoke(id: String): Flow<AppResult<CryptoCurrencyModel, ErrorType>> {
        return cryptoCurrencyDataRepository.getCryptoCurrencyDetails(id).map { result ->
            when (result) {
                is AppResult.Success -> {
                    AppResult.Success(
                        mapCryptoCurrencyInfoDetailsEntityToCryptoCurrenciesInfoDataModel(result.data)
                    )
                }

                is AppResult.Error -> {
                    AppResult.Error(result.error)
                }
            }
        }
    }

    private fun mapCryptoCurrencyInfoDetailsEntityToCryptoCurrenciesInfoDataModel(
        cryptoCurrencyDetailsDataEntity: CryptoCurrencyDetailsDataEntity
    ): CryptoCurrencyModel {
        val cryptoData = cryptoCurrencyDetailsDataEntity.cryptoCurrencyInfoData
        return CryptoCurrencyModel(
            id = cryptoData.id,
            symbol = cryptoData.symbol,
            name = cryptoData.name,
            priceUsd = formatDecimalUseCase.invoke(cryptoData.priceUsd),
            changePercent24Hr = roundChangePercent24HrToTwoDecimalPlaces(cryptoData.changePercent24Hr),
            supply = formatDecimalUseCase.invoke(cryptoData.supply)
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
