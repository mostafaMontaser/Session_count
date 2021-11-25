package com.challenge.patagonian.view

import androidx.fragment.app.Fragment
import com.challenge.patagonian.view.BaseActivity

abstract class BaseFragment : Fragment() {
    override fun onDestroyView() {
        super.onDestroyView()
        if (activity != null && activity is BaseActivity && requireActivity().supportFragmentManager
                .backStackEntryCount > 0
        ) {
            (activity as BaseActivity).notifyFragmentsAppearing()
        }
    }

    open fun didAppear() {}
}