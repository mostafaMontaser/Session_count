package com.challenge.patagonian.dispatcher

import com.challenge.patagonian.model.request.BaseRequestFactory
import com.challenge.patagonian.model.response.ResponseException
import com.challenge.patagonian.repository.local.BaseLocalRepo
import com.challenge.patagonian.repository.remote.BaseRemoteRepo
import com.challenge.patagonian.util.network.NetworkUtil
import java.lang.reflect.Type
import java.util.*

interface BaseDispatcher {
    val localRepo: BaseLocalRepo
    val remoteRepo: BaseRemoteRepo
    suspend fun fetchData(cash: Boolean, type: Type, requestFactory: BaseRequestFactory): Any? {
        var errorMessage: String? = null
        var responseCode: String? = null
        val response = try {
            val isNetworkConnected = NetworkUtil.isNetworkAvailable()
            if (isNetworkConnected) {
                remoteRepo.fetchData(requestFactory)
            } else {
                errorMessage = NetworkUtil.NETWORK_ERROR_MSG
                responseCode = NetworkUtil.NO_INTERNET_CONNECTION_CODE
                null
            }
        } catch (ex: Exception) {
            errorMessage = NetworkUtil.CLIENT_ERROR_MSG
            responseCode = ""
            ex.printStackTrace()
            null
        }
        if (response != null) {
            if (response.isSuccessful) {
                val cache = response.body()
                if (cash) {
                    localRepo.saveObject(cache, type)
                }
                return cache
            } else {
                errorMessage = response.errorBody()?.toString()
                responseCode = "" + response.code()
            }
        } else if (cash) {
            val cashed = localRepo.getCashedObject(type)
            if (cashed != null)
                return cashed
        }
        throw ResponseException(
            message = errorMessage,
            responseCode = responseCode,
            endPoint = requestFactory.getEndPoint()
        )
    }

    fun getCashedObject(type: Type): Any? = localRepo.getCashedObject(type)

    fun saveObject(instance: Any?, type: Type, lastModifiedDate: Long = Date().time) =
        localRepo.saveObject(instance, type, lastModifiedDate)
}



