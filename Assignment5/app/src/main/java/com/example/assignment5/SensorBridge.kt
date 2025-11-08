package com.example.assignment5

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

// wraps sensor callbacks to keep code stay clean
class SensorBridge(
    ctx: Context,
    private val onSample: (ax: Float, ay: Float, dt: Float) -> Unit
) : SensorEventListener {

    private val mgr = ctx.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gravity = mgr.getDefaultSensor(Sensor.TYPE_GRAVITY)
    private val accel   = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // prefer gravity - fall back to accelerometer
    private val sensor: Sensor? = gravity ?: accel
    private val usingAccelerometer = (sensor?.type == Sensor.TYPE_ACCELEROMETER)

    private var lastNs = 0L
    private val lp: LowPassFilter? = if (usingAccelerometer) LowPassFilter(alpha = 0.12f) else null

    fun start() {
        sensor?.let { mgr.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME) }
        lastNs = 0L
        lp?.reset()
    }

    fun stop() {
        mgr.unregisterListener(this)
        lastNs = 0L
        lp?.reset()
    }

    override fun onSensorChanged(e: SensorEvent) {
        val now = e.timestamp
        val dt = if (lastNs == 0L) 0f else (now - lastNs) / 1_000_000_000f
        lastNs = now

        var ax = e.values[0]
        var ay = e.values[1]

        // smooth only when on accelerometer (gravity is already filtered)
        if (usingAccelerometer) {
            val (fx, fy) = lp!!.filter(ax, ay)
            ax = fx
            ay = fy
        }

        onSample(ax, ay, dt)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
