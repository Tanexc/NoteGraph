package ru.tanexc.notegraph.presentation.ui.widgets.note

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.chihsuanwu.freescroll.FreeScrollState
import com.chihsuanwu.freescroll.freeScroll

@Composable
fun ExpandableField(
    modifier: Modifier,
    initialSize: IntSize,
    scrollState: FreeScrollState,
    onSizeChanged: (IntSize) -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    var width by remember { mutableIntStateOf(initialSize.width) }
    var height by remember { mutableIntStateOf(initialSize.height) }

    LaunchedEffect(
        key1 = scrollState.verticalScrollState.canScrollBackward || scrollState.verticalScrollState.isScrollInProgress,
        key2 = scrollState.horizontalScrollState.canScrollBackward || scrollState.horizontalScrollState.isScrollInProgress
    ) {
        if (!scrollState.verticalScrollState.canScrollBackward) {
            width += 88
            onSizeChanged(IntSize(width, height))
        }
        if (!scrollState.horizontalScrollState.canScrollBackward) {
            height += 176
            onSizeChanged(IntSize(width, height))
        }
    }

    Box(
        modifier
            .size(width.dp, height.dp)
            .freeScroll(scrollState)
    ) {
        Row(Modifier.fillMaxSize()) {
            repeat((width / 84)) {
                Spacer(
                    modifier = Modifier.size(42.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(Color.LightGray.copy(0.05f))
                )
                Spacer(
                    modifier = Modifier.size(42.dp)
                )
            }
        }

        Column(Modifier.fillMaxSize()) {
            repeat((height / 84)) {
                Spacer(
                    modifier = Modifier.size(42.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray.copy(0.05f))
                )
                Spacer(
                    modifier = Modifier.size(42.dp)
                )
            }
        }

        content()
    }
}