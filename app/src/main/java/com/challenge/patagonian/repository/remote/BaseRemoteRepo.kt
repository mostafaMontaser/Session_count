package com.challenge.patagonian.repository.remote

import com.challenge.patagonian.model.request.BaseRequestFactory
import com.challenge.patagonian.model.response.BaseModel
import retrofit2.Response

interface BaseRemoteRepo {
      suspend fun  fetchData(requestFactory: BaseRequestFactory): Response<out BaseModel>?
}