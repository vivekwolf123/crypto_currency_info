package com.client.android.core_crypto_currency_domain.usecase

import com.client.android.core_base.AppResult
import com.client.android.common_utils.ErrorType
import com.client.android.common_utils.roundChangePercent24HrToTwoDecimalPlaces
import com.client.android.core_crypto_currency_data.CryptoCurrencyDataRepository
import com.client.android.core_crypto_currency_data.entity.CryptoCurrenciesDataEntity
import com.client.android.core_crypto_currency_domain.model.CryptoCurrenciesModel
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTopCryptoCurrenciesUseCase @Inject constructor(
    private val cryptoCurrencyDataRepository: CryptoCurrencyDataRepository,
    private val formatDecimalUseCase: FormatDecimalUseCase
) {
    suspend operator fun invoke(): Flow<AppResult<CryptoCurrenciesModel, ErrorType>> {
        return cryptoCurrencyDataRepository.getTopCryptoCurrencies().map { result ->
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
        cryptoCurrenciesDataEntity: CryptoCurrenciesDataEntity
    ): CryptoCurrenciesModel {
        return CryptoCurrenciesModel(
            data = cryptoCurrenciesDataEntity.data.map { data ->
                CryptoCurrencyModel(
                    id = data.id,
                    symbol = data.symbol,
                    name = data.name,
                    priceUsd = formatDecimalUseCase.invoke(data.priceUsd),
                    changePercent24Hr = data.changePercent24Hr.roundChangePercent24HrToTwoDecimalPlaces(),
                    supply = data.supply
                )
            }
        )
    }
}
