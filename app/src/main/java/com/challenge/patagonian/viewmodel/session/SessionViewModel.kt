package com.challenge.patagonian.viewmodel.session

import android.hardware.SensorEvent
import androidx.lifecycle.MutableLiveData
import com.challenge.patagonian.dispatcher.BaseDispatcher
import com.challenge.patagonian.dispatcher.session.SessionDispatcher
import com.challenge.patagonian.model.session.SessionModel
import com.challenge.patagonian.model.session.TextSize
import com.challenge.patagonian.viewmodel.BaseViewModel
import java.util.*

class SessionViewModel(private val sessionDispatcher: SessionDispatcher) : BaseViewModel() {
    override fun getDispatcher(): BaseDispatcher = sessionDispatcher
    var updateTextLiveData = MutableLiveData<TextSize>()
    private var initZAngle: Double? = null
    fun getSessionCount(): Int {
        var sessionCount = 1
        val sessionModel =
            sessionDispatcher.getCashedObject(SessionModel::class.java) as SessionModel?
        sessionModel?.let {
            val diff: Long = Date().time - sessionModel.disappearingTime
            val minutes = diff / 1000 / 60
            if (minutes >= 10) {
                sessionModel.sessionCount = sessionModel.sessionCount + 1
                sessionCount = sessionModel.sessionCount
                sessionDispatcher.saveObject(sessionModel, SessionModel::class.java)
            }
            sessionCount = sessionModel.sessionCount
        }

        return sessionCount
    }

    fun updateSensorLogic(event: SensorEvent?) {
        if (event != null) {
            // Axis of the rotation sample, not normalized yet.
            var axisX: Float = event.values[0]
            var axisY: Float = event.values[1]
            val axisZ: Float = event.values[2]
            if (initZAngle == null) {
                initZAngle = axisZ * 180 / Math.PI
            }
            val currentZAngle = axisZ * 180 / Math.PI
            initZAngle?.let {
                if (currentZAngle - it > 30) {
                    updateTextLiveData.postValue(TextSize.DECREASE)
                } else if (it - currentZAngle > 30) {
                    updateTextLiveData.postValue(TextSize.INCREASE)
                }
            }
        }
    }

    fun setDisappear() {
        var sessionCount = 1
        val sessionModel =
            sessionDispatcher.getCashedObject(SessionModel::class.java) as SessionModel?
        sessionModel?.let {
            sessionCount = sessionModel.sessionCount
        }
        sessionDispatcher.saveObject(
            SessionModel(Date().time, sessionCount),
            SessionModel::class.java
        )

    }
}