package com.example.assignment5

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MarbleViewModel : ViewModel() {
    // ui state
    private val _pos = MutableStateFlow(Offset.Zero)
    val pos: StateFlow<Offset> = _pos

    // runtime bounds from the composable
    var bounds: IntSize = IntSize.Zero

    // marble radius - px set by ui
    var radiusPx: Float = 24f

    // velocity integrator
    private var vx = 0f
    private var vy = 0f

    // tuning
    var friction: Float = 0.98f   // 0.9 = sticky, 0.99 = glide
    var scale: Float = 1.0f       // multiply sensor units → px
    var bottomInsetPx: Float = 0f

    fun onSensor(ax: Float, ay: Float, dt: Float) {
        if (dt <= 0f) return

        // sensor x: left -> right; sensor y: up (compose y grows down) → invert y
        vx = (vx + (-ax) * scale * dt) * friction
        vy = (vy + ( ay) * scale * dt) * friction

        val next = Offset(_pos.value.x + vx, _pos.value.y + vy)
        _pos.value = clampToBounds(next)
    }

    fun reset(center: Boolean = true) {
        vx = 0f
        vy = 0f
        if (center && bounds != IntSize.Zero) {
            val cx = bounds.width / 2f
            val cy = bounds.height / 2f
            _pos.value = clampToBounds(Offset(cx, cy))
        }
    }

    fun updateFriction(value: Float) {
        friction = value.coerceIn(0.85f, 0.999f)
    }

    fun updateScale(value: Float) {
        scale = value.coerceIn(0.2f, 5.0f)
    }


    private fun clampToBounds(p: Offset): Offset {
        if (bounds == IntSize.Zero) return p
        val minX = 0f + radiusPx
        val minY = 0f + radiusPx
        val maxX = bounds.width - radiusPx
        val maxY = bounds.height - radiusPx - bottomInsetPx.coerceAtLeast(0f)
        val x = p.x.coerceIn(minX, maxX)
        val y = p.y.coerceIn(minY, maxY)
        return Offset(x, y)
    }
}