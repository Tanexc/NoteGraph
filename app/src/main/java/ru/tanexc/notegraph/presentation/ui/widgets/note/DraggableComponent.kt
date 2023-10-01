package ru.tanexc.notegraph.presentation.ui.widgets.note

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@Composable
fun DraggableComponent(
    startOffset: IntOffset,
    enabled: Boolean = true,
    enabledIndication: Modifier.() ->Modifier,
    onOffsetChange: ((IntOffset) -> Unit)? = null,
    content: @Composable () -> Unit
) {
    var offset: IntOffset by remember { mutableStateOf(startOffset) }
    Box(
        content = { content() },
        modifier =
        if (enabled) Modifier
            .offset { offset }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val offsetChange = IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt())
                    if ((offset + offsetChange).x > 0 && (offset + offsetChange).y > 0) {
                        offset += offsetChange
                        onOffsetChange?.let { it(offset) }
                    }
                }
            }
            .enabledIndication()
        else Modifier.offset { offset }
    )
}