package com.challenge.patagonian.viewmodel

import com.challenge.patagonian.dispatcher.BaseDispatcher
import com.challenge.patagonian.model.request.BaseRequestFactory
import com.challenge.patagonian.model.response.ErrorScreen
import com.challenge.patagonian.model.response.ResponseException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.lang.reflect.Type

abstract class BaseViewModel() : ViewModel() {
    var errorDialog: MutableLiveData<ErrorScreen> = MutableLiveData()
    var showFullLoading: MutableLiveData<Boolean> = MutableLiveData()
    private var connectJob: Job? = null


    fun fetchData(
        cash: Boolean, type: Type, requestFactory: BaseRequestFactory,
        proceedResponse: (t: Any?) -> Unit
    ) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            if (throwable is ResponseException)
                onError(ErrorScreen(throwable.message, throwable.responseCode))
        }
        showFullLoading.value = true
        connectJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = getDispatcher()?.fetchData(cash, type, requestFactory)
            withContext(Dispatchers.Main) {
                proceedResponse(response)
                showFullLoading.value = false
            }
        }
    }

    private fun onError(errorScreen: ErrorScreen) {
        errorDialog.postValue(errorScreen)
        showFullLoading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        connectJob?.cancel()
    }

    abstract fun getDispatcher(): BaseDispatcher?
}