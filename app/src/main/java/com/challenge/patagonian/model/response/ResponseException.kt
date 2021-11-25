package com.challenge.patagonian.model.response


data class ResponseException(
    override var message: String? = "",
    var responseCode: String? = "",
    var endPoint: String? = ""
) : Exception()