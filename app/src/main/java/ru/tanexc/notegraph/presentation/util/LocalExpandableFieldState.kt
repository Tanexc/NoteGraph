package ru.tanexc.notegraph.presentation.util

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.chihsuanwu.freescroll.FreeScrollState
import com.chihsuanwu.freescroll.freeScrollWithTransformGesture

class ExpandableFieldState {
    private var parameters: ExpandableFieldParams by mutableStateOf(
        ExpandableFieldParams.default()
    )

    private val scrollState: FreeScrollState = FreeScrollState(ScrollState(0), ScrollState(0))

    fun width(): Int = parameters.width

    fun height(): Int = parameters.height

    fun zoom(): Float = parameters.zoom

    fun cellSize(): Int = parameters.cellSize

    fun cellColor(): Color = parameters.cellColor

    fun zoomedHeight(): Int = (parameters.zoom * parameters.height).toInt()

    fun zoomedWidth(): Int = (parameters.zoom * parameters.width).toInt()

    fun zoomedCellSize(): Int = (parameters.zoom * parameters.cellSize).toInt()

    fun size(zoomed: Boolean): DpSize = DpSize(
        (if (zoomed) zoomedWidth() else width()).toInt().dp,
        (if (zoomed) zoomedHeight() else height()).toInt().dp
    )

    fun cellsInHeight(): Int = height() / cellSize()

    fun cellsInWidth(): Int = width() / cellSize()

    fun updateParameters(
        zoom: Float = parameters.zoom,
        width: Int = parameters.width,
        height: Int = parameters.height,
        cellSize: Int = parameters.cellSize,
        cellColor: Color = parameters.cellColor,
        onSizeChanged: (IntSize) -> Unit = parameters.onSizeChanged
    ) {
        parameters = ExpandableFieldParams(
            zoom, width, height, cellSize, cellColor, onSizeChanged
        )
    }

    @Composable
    fun provideFreeScrollState(): FreeScrollState {
        LaunchedEffect(
            key1 = scrollState.xValue == scrollState.xMaxValue,
            key2 = scrollState.yValue == scrollState.yMaxValue
        ) {
            updateParameters(
                width = width() + 100 * (if (scrollState.xValue == scrollState.xMaxValue) 1 else 0),
                height = height() + 100 * (if (scrollState.yValue == scrollState.yMaxValue) 1 else 0)
            )
            parameters.onSizeChanged(IntSize(width(), height()))
        }

        return remember { scrollState }
    }


    fun zoomable(state: FreeScrollState) = Modifier
        .freeScrollWithTransformGesture(state) { _, _, value, _ ->
            if (value != 1f) {
                updateParameters(zoom = zoom() + value - 1f)
            }

        }

}

data class ExpandableFieldParams(
    val zoom: Float,
    val width: Int,
    val height: Int,
    val cellSize: Int,
    val cellColor: Color,
    val onSizeChanged: (IntSize) -> Unit
) {
    companion object {
        fun default() = ExpandableFieldParams(
            zoom = 1f,
            width = 560,
            height = 720,
            cellSize = 80,
            cellColor = Color.LightGray.copy(0.05f),
            onSizeChanged = {}
        )
    }
}

val LocalExpandableFieldState = compositionLocalOf { ExpandableFieldState() }

@Composable
fun rememberExpandableFieldState(
    zoom: Float = LocalExpandableFieldState.current.zoom(),
    width: Int = LocalExpandableFieldState.current.width(),
    height: Int = LocalExpandableFieldState.current.height(),
    cellSize: Int = LocalExpandableFieldState.current.cellSize(),
    cellColor: Color = LocalExpandableFieldState.current.cellColor(),
    onSizeChanged: (IntSize) -> Unit = {}
): ProvidableCompositionLocal<ExpandableFieldState> {
    LocalExpandableFieldState.current.updateParameters(
        zoom = zoom,
        width = width,
        height = height,
        cellSize = cellSize,
        cellColor = cellColor,
        onSizeChanged = onSizeChanged
    )
    return remember { LocalExpandableFieldState }
}