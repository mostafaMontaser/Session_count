package com.challenge.patagonian.view.util

import com.challenge.patagonian.model.response.ErrorScreen

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun showError(error: ErrorScreen)
}