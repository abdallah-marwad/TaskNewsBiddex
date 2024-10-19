package com.tailors.doctoria.application.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskbiddex.common.utils.ErrorType
import com.example.taskbiddex.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
    created at 01/01/2024
    by Abdallah Marwad
    abdallahshehata311as@gmail.com
 */
@HiltViewModel
open class BaseViewModel @Inject constructor(
) : ViewModel() {
    protected val _noInternet by lazy { Channel<String>() }
    val noInternet: Flow<String> by lazy { _noInternet.receiveAsFlow() }
    protected val _unAuthorized by lazy { Channel<String>() }
    val unAuthorized: Flow<String> by lazy { _unAuthorized.receiveAsFlow() }
    fun <T> handleFailure(err: ErrorType?, callback: Channel<Resource<T>>) {
        viewModelScope.launch {
            when (err) {
                is ErrorType.NoInternet -> {
                    _noInternet.send(err.message.toString())
                }
                is ErrorType.GeneralErr -> {
                    callback.send(Resource.Failure(ErrorType.GeneralErr(err.message)))
                }
                null -> {}
            }
        }
    }
}
