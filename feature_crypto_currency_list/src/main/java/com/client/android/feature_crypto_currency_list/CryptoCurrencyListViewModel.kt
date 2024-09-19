package com.client.android.feature_crypto_currency_list

import androidx.lifecycle.viewModelScope
import com.client.android.core_base.AppResult
import com.client.android.common_utils.ErrorType
import com.client.android.core_crypto_currency_domain.usecase.GetTopCryptoCurrenciesUseCase
import com.client.android.feature_base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoCurrencyListViewModel @Inject constructor(private val getTopCryptoCurrenciesUseCase: GetTopCryptoCurrenciesUseCase) :
    BaseViewModel<CryptoCurrencyListViewState, CryptoCurrencyListViewEvent, CryptoCurrencyListViewEffect>(
        initialState = CryptoCurrencyListViewState(loading = true),
        reducer = CryptoCurrencyListReducer()
    ) {

    init {
        viewModelScope.launch {
            getTopCryptoCurrenciesUseCase.invoke().onStart {
                sendEvent(CryptoCurrencyListViewEvent.GetTopCryptoCurrencyList)
            }.catch {
                sendEvent(CryptoCurrencyListViewEvent.OnTopCryptoCurrencyListFetchError(error = ErrorType.UNKNOWN_ERROR))
            }.collect {
                when (it) {
                    is AppResult.Success -> {
                        if (it.data.data.isNotEmpty()) {
                            sendEvent(
                                CryptoCurrencyListViewEvent.OnTopCryptoCurrencyListFetchSuccess(
                                    cryptoCurrenciesModel = it.data
                                )
                            )
                        } else {
                            sendEvent(
                                CryptoCurrencyListViewEvent.OnTopCryptoCurrencyListFetchError(
                                    error = ErrorType.CRYPTO_CURRENCY_INFO_LIST_EMPTY_ERROR
                                )
                            )
                        }
                    }

                    is AppResult.Error -> {
                        sendEvent(
                            CryptoCurrencyListViewEvent.OnTopCryptoCurrencyListFetchError(
                                error = it.error
                            )
                        )
                    }
                }
            }
        }
    }
}
