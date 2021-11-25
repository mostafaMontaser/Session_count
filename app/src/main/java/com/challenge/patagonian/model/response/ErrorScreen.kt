package com.challenge.patagonian.model.response

data class ErrorScreen(
    val title: String,
    val message: String?,
    val responseCode: String?,
    val renderType: String
) {
    constructor(message: String?, responseCode: String?) : this("", message, responseCode, "")
}