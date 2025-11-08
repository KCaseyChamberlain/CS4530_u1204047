package com.example.assignment5

// simple low-pass filter to smooth noisy accel readings
// y_t = alpha * x_t + (1 - alpha) * y_{t-1}
class LowPassFilter(private val alpha: Float = 0.12f) {
    private var hasPrev = false
    private var prevX = 0f
    private var prevY = 0f

    fun filter(ax: Float, ay: Float): Pair<Float, Float> {
        if (!hasPrev) {
            prevX = ax
            prevY = ay
            hasPrev = true
            return ax to ay
        }
        prevX = alpha * ax + (1f - alpha) * prevX
        prevY = alpha * ay + (1f - alpha) * prevY
        return prevX to prevY
    }

    fun reset() {
        hasPrev = false
        prevX = 0f
        prevY = 0f
    }
}