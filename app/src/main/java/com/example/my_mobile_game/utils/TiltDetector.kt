package com.example.my_mobile_game.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.my_mobile_game.interfaces.TiltCallback
import kotlin.math.abs

class TiltDetector(context: Context, private val tiltCallback: TiltCallback?) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) as Sensor
    private lateinit var sensorEventListener: SensorEventListener


    private var timestamp: Long = 0L

    init {
        initEventListener()
    }

    private fun initEventListener() {
        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                calculateTilt(x, y)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // pass
            }
        }
    }

    private fun calculateTilt(x: Float, y: Float) {
        if (System.currentTimeMillis() - timestamp >= 500) { // Enough time passed since last check
            timestamp = System.currentTimeMillis()

            if (abs(x) >= 3.0) {
                if (x > 0) {
                    tiltCallback?.tiltLeft()
                } else {
                    tiltCallback?.tiltRight()
                }
            }

            if (abs(y) >= 3.0) {
                if (y > 0) {
                    tiltCallback?.tiltUp()
                } else {
                    tiltCallback?.tiltDown()
                }
            }
        }
    }


    fun start() {
        sensorManager
            .registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
    }


    fun stop() {
        sensorManager
            .unregisterListener(
                sensorEventListener,
                sensor
            )
    }

}