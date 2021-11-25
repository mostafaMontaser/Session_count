package com.challenge.patagonian.view

import android.os.Bundle
import com.challenge.patagonian.model.response.ErrorScreen
import com.challenge.patagonian.view.util.BaseView
import com.challenge.patagonian.viewmodel.BaseViewModel
import android.widget.Toast
import androidx.lifecycle.Observer


abstract class BasicFragment : BaseFragment(), BaseView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel()?.showFullLoading?.observe(this, showFullLoading)
        getViewModel()?.errorDialog?.observe(this, showErrorUi)
    }

    override fun showError(error: ErrorScreen) {
        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
    }

    abstract fun getViewModel(): BaseViewModel?
    private val showFullLoading = Observer<Boolean> {
        if (it)
            showLoading()
        else
            hideLoading()
    }

    private val showErrorUi = Observer<ErrorScreen> { showError(it) }

}