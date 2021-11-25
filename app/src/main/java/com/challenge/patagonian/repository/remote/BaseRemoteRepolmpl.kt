package com.challenge.patagonian.repository.remote

import com.challenge.patagonian.model.request.BaseRequestFactory
import com.challenge.patagonian.model.response.BaseModel
import retrofit2.Response

class BaseRemoteRepoImpl : BaseRemoteRepo {
    override suspend fun fetchData(requestFactory: BaseRequestFactory): Response<out BaseModel>? =null
}