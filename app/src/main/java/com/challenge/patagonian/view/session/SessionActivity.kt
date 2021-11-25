package com.challenge.patagonian.view.session

import android.os.Bundle
import com.challenge.patagonian.view.BaseActivity
import com.challenge.patagonian.R
import com.challenge.patagonian.databinding.ActivitySessionBinding

class SessionActivity : BaseActivity() {
    private lateinit var binding: ActivitySessionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySessionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(R.id.layout_container, SessionFragment())
            .commit()
    }

    override fun getContainerId(): Int {
        return R.id.layout_container
    }
}