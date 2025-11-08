package com.example.assignment5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {

    private val vm: MarbleViewModel by viewModels()
    private lateinit var sensor: SensorBridge

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensor = SensorBridge(this) { ax, ay, dt ->
            vm.onSensor(ax, ay, dt)
        }

        setContent {
            MaterialTheme { MarbleScreen(vm) }
        }
    }
    override fun onResume() {
        super.onResume()
        sensor.start()
    }

    override fun onPause() {
        sensor.stop()
        super.onPause()
    }
}

@Composable
fun MarbleScreen(vm: MarbleViewModel) {
    val pos by vm.pos.collectAsState()
    val marbleDiameter = 48.dp
    val density = LocalDensity.current
    var initialized by remember { mutableStateOf(false) }

    // measured height of the bottom controls (in px)
    var controlsHeightPx by remember { mutableStateOf(0f) }

    // push inset to vm whenever it changes
    LaunchedEffect(controlsHeightPx) {
        vm.bottomInsetPx = controlsHeightPx
    }

    BoxWithConstraints(Modifier.fillMaxSize()) {
        // update bounds and radius when size/density changes
        LaunchedEffect(maxWidth, maxHeight, density) {
            val wPx = with(density) { maxWidth.toPx().roundToInt() }
            val hPx = with(density) { maxHeight.toPx().roundToInt() }
            vm.bounds = IntSize(wPx, hPx)
            vm.radiusPx = with(density) { (marbleDiameter / 2).toPx() }
            if (!initialized) {
                vm.reset(center = true)
                initialized = true
            }
        }

        // marble
        Box(
            Modifier
                .offset { IntOffset(pos.x.roundToInt(), pos.y.roundToInt()) }
                .size(marbleDiameter)
                .background(Color.DarkGray, CircleShape)
        )

        // overlay ui
        Box(Modifier.fillMaxSize()) {
            // reset button
            Button(
                onClick = { vm.reset(center = true) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) { Text("Reset") }

            // controls panel - measured height to exclude from play area
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                    .padding(horizontal = 8.dp, vertical = 6.dp)
                    .onGloballyPositioned { coords ->
                        controlsHeightPx = coords.size.height.toFloat()
                    }
            ) {
                ControlsPanel(vm, Modifier.fillMaxWidth())
            }
        }
    }
}