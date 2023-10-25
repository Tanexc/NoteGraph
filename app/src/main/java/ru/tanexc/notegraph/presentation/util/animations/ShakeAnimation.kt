package ru.tanexc.notegraph.presentation.util.animations

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo

fun Modifier.shakeAnimation(
    enabled: Boolean,
    duration: Int = 500,
    targetScale: Float = 0.97f
) = composed(
    factory = {
        val scale by animateFloatAsState(
            targetValue = if (enabled) targetScale else 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = duration, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )

        Modifier.graphicsLayer {
            scaleX = if (enabled) scale else 1f
            scaleY = if (enabled) scale else 1f
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "shakeAnimation"
        properties["enabled"] = enabled
    }
)