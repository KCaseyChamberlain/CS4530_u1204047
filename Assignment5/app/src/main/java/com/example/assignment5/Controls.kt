package com.example.assignment5

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.round

@Composable
fun ControlsPanel(vm: MarbleViewModel, modifier: Modifier = Modifier) {
    Column(modifier.padding(12.dp)) {
        // friction slider
        var f by remember { mutableFloatStateOf(vm.friction) }
        Text("friction: ${round(f * 1000f) / 1000f}")
        Slider(
            value = f,
            onValueChange = {
                f = it
                vm.updateFriction(it)
            },
            valueRange = 0.90f..0.999f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // scale slider
        var s by remember { mutableFloatStateOf(vm.scale) }
        Text("scale: ${round(s * 100f) / 100f}")
        Slider(
            value = s,
            onValueChange = {
                s = it
                vm.updateScale(it)
            },
            valueRange = 0.5f..3.0f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}