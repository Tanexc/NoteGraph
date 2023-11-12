package ru.tanexc.notegraph.presentation.ui.widgets.note

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.chihsuanwu.freescroll.FreeScrollState
import com.chihsuanwu.freescroll.freeScroll
import com.chihsuanwu.freescroll.freeScrollWithTransformGesture
import com.chihsuanwu.freescroll.rememberFreeScrollState
import com.smarttoolfactory.zoom.rememberZoomState
import kotlinx.coroutines.runBlocking
import ru.tanexc.notegraph.presentation.util.ExpandableFieldState
import ru.tanexc.notegraph.presentation.util.rememberExpandableFieldState

@Composable
fun ExpandableField(
    modifier: Modifier = Modifier,
    state: ProvidableCompositionLocal<ExpandableFieldState> = rememberExpandableFieldState(),
    content: @Composable BoxScope.(zoom: Float) -> Unit
) {

    Box(
        state.current.zoomable(state.current.provideFreeScrollState())
    ) {
        Box(
            modifier
                .size(state.current.size(true))
        ) {
            Row(Modifier.fillMaxSize()) {
                repeat(state.current.cellsInWidth()) {
                    Spacer(
                        modifier = Modifier.size(state.current.zoomedCellSize().dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(state.current.cellColor())
                    )
                    Spacer(
                        modifier = Modifier.size(state.current.zoomedCellSize().dp)
                    )
                }
            }
            Column(Modifier.fillMaxSize()) {
                repeat(state.current.cellsInHeight()) {
                    Spacer(
                        modifier = Modifier.size(state.current.zoomedCellSize().dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray.copy(0.05f))
                    )
                    Spacer(
                        modifier = Modifier.size(state.current.zoomedCellSize().dp)
                    )
                }
            }
        }
        content(state.current.zoom())
    }

}