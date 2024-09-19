package com.client.android.core_crypto_currency_domain.usecase

import com.client.android.core_base.AppResult
import com.client.android.common_utils.ErrorType
import com.client.android.common_utils.roundChangePercent24HrToTwoDecimalPlaces
import com.client.android.core_crypto_currency_data.CryptoCurrencyDataRepository
import com.client.android.core_crypto_currency_data.entity.CryptoCurrencyDetailsDataEntity
import com.client.android.core_crypto_currency_domain.model.CryptoCurrencyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCryptoCurrencyDetailsUseCase @Inject constructor(
    private val cryptoCurrencyDataRepository: CryptoCurrencyDataRepository,
    private val formatDecimalUseCase: FormatDecimalUseCase
) {
    suspend operator fun invoke(cryptoCurrencyDetailsId: String): Flow<AppResult<CryptoCurrencyModel, ErrorType>> {
        return cryptoCurrencyDataRepository.getCryptoCurrencyDetails(cryptoCurrencyDetailsId).map { result ->
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
            changePercent24Hr = cryptoData.changePercent24Hr.roundChangePercent24HrToTwoDecimalPlaces(),
            supply = formatDecimalUseCase.invoke(cryptoData.supply),
            marketCapUsd = formatDecimalUseCase.invoke(cryptoData.marketCapUsd),
            volumeUsd24Hr = formatDecimalUseCase.invoke(cryptoData.volumeUsd24Hr),
        )
    }
}
