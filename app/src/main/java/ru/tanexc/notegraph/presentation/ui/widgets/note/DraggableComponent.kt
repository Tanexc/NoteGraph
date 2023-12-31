package ru.tanexc.notegraph.presentation.ui.widgets.note

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
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
    modifier: Modifier,
    startOffset: IntOffset,
    enabled: Boolean = true,
    onOffsetChange: ((IntOffset) -> Unit)? = null,
    onRelease: (IntOffset) -> Unit,
    content: @Composable (BoxScope.(Boolean) -> Unit)
) {
    var offset: IntOffset by remember { mutableStateOf(startOffset) }
    Column(
        modifier = Modifier
            .offset { offset }
            .run {
                if (enabled) {
                    this.pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = { onRelease(offset) }
                        ) { change, dragAmount ->
                            change.consume()
                            val offsetChange =
                                IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt())
                            if ((offset + offsetChange).x > 0 && (offset + offsetChange).y > 0) {
                                offset += offsetChange
                                onOffsetChange?.let { it(offset) }
                            }
                        }
                    }
                } else this
            }.then(modifier)

    ) {
        Box(
            content = { content(enabled) }
        )
    }
}