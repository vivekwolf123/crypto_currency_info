package com.client.android.feature_crypto_currency_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.client.android.core_base.AppResult
import com.client.android.common_utils.ErrorType
import com.client.android.core_crypto_currency_domain.usecase.GetCryptoCurrencyDetailsUseCase
import com.client.android.feature_base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoCurrencyDetailsViewModel @Inject constructor(
    private val getCryptoCurrencyDetailsUseCase: GetCryptoCurrencyDetailsUseCase,
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<CryptoCurrencyDetailsViewState, CryptoCurrencyDetailsViewEvent, CryptoCurrencyDetailsViewEffect>(
        initialState = CryptoCurrencyDetailsViewState(loading = true),
        reducer = CryptoCurrencyDetailsReducer()
    ) {

    private val cryptoCurrencyDetailsId: String? =
        savedStateHandle.get<String>("cryptoCurrencyDetailsId")

    init {
        viewModelScope.launch {
            if (cryptoCurrencyDetailsId.isNullOrEmpty()) {
                sendEvent(CryptoCurrencyDetailsViewEvent.OnCryptoCurrencyDetailsFetchError(error = ErrorType.CRYPTO_CURRENCY_INFO_DETAILS_MISSING_ERROR))
            } else {
                getCryptoCurrencyDetailsUseCase.invoke(cryptoCurrencyDetailsId).onStart {
                    sendEvent(CryptoCurrencyDetailsViewEvent.GetCryptoCurrencyDetails)
                }.catch {
                    sendEvent(CryptoCurrencyDetailsViewEvent.OnCryptoCurrencyDetailsFetchError(error = ErrorType.UNKNOWN_ERROR))
                }
                    .collect {
                        when (it) {
                            is AppResult.Success -> {
                                sendEvent(
                                    CryptoCurrencyDetailsViewEvent.OnCryptoCurrencyDetailsFetchedSuccess(
                                        cryptoCurrencyModel = it.data
                                    )
                                )
                            }

                            is AppResult.Error -> {
                                sendEvent(
                                    CryptoCurrencyDetailsViewEvent.OnCryptoCurrencyDetailsFetchError(
                                        error = it.error
                                    )
                                )
                            }
                        }
                    }
            }
        }
    }
}
