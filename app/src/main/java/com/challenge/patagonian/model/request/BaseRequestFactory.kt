package com.challenge.patagonian.model.request


abstract class BaseRequestFactory() {

    private var defaultProperties = HashMap<String, Any>()

    init {
        defaultProperties["Content-Type"] = "application/json"

    }
    fun getUrl() = Constant.BASE_URL + getEndPoint()
    abstract fun getEndPoint(): String?
    abstract fun getRequestParam(): BaseRequestParam
    fun getHeaderParam(): HashMap<String, Any> {
        val headers = HashMap<String, Any>()
        headers.putAll(defaultProperties)
        headers.putAll(getCustomHeaders())
        return headers
    }

    open fun getCustomHeaders(): HashMap<String, Any> = HashMap()
}