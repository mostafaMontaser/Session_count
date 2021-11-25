package com.challenge.patagonian.model.retrofit

import com.challenge.patagonian.model.request.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    fun getService(): Api =
        Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
}
