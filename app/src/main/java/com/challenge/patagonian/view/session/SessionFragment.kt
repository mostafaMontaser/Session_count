package com.challenge.patagonian.view.session

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.challenge.patagonian.R
import com.challenge.patagonian.databinding.FragmentSessionBinding
import com.challenge.patagonian.util.extention.gone
import com.challenge.patagonian.util.extention.show
import com.challenge.patagonian.view.BasicFragment
import com.challenge.patagonian.viewmodel.BaseViewModel
import com.challenge.patagonian.viewmodel.session.SessionViewModel
import org.koin.android.ext.android.inject

class SessionFragment : BasicFragment(), SensorEventListener {
    private val viewModel: SessionViewModel by inject()
    override fun getViewModel(): BaseViewModel = viewModel
    private lateinit var binding: FragmentSessionBinding

    override fun showLoading() {
        binding.loadingView.show()
    }

    override fun hideLoading() {
        binding.loadingView.gone()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentSessionBinding.inflate(inflater, container, false);
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        val sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (sensor != null) {
            sensorManager.registerListener(
                this, sensor,
                SensorManager.SENSOR_DELAY_GAME
            );
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.setDisappear()
    }

    override fun onResume() {
        super.onResume()
        binding.txtSessionCount.text = getString(R.string.session_count, viewModel.getSessionCount())
    }

    override fun onDestroy() {
        super.onDestroy()
        val sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        viewModel.updateSensorLogic(sensorEvent)
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {
    }

    private fun observeViewModel() {
        viewModel.updateTextLiveData.observe(this, {
            it?.let {
                binding.txtSessionCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, it.textSize)
            }
        })
    }
}