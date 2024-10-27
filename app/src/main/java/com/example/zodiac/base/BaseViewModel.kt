package com.example.zodiac.base

import androidx.lifecycle.ViewModel
import com.example.zodiac.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel : ViewModel() {
    protected fun <T> handleLoading(state: MutableStateFlow<NetworkResult<T>>) {
        state.value = NetworkResult.Loading()
    }

    protected fun <T> handleError(state: MutableStateFlow<NetworkResult<T>>, message: String) {
        state.value = NetworkResult.Error(message)
    }
}