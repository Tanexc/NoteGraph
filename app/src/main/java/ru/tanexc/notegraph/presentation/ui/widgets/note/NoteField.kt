package ru.tanexc.notegraph.presentation.ui.widgets.note

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chihsuanwu.freescroll.freeScroll
import com.chihsuanwu.freescroll.rememberFreeScrollState
import ru.tanexc.notegraph.domain.model.Note
import ru.tanexc.notegraph.domain.model.ImagePiece

@Composable
fun NoteField(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    val noteScrollState = rememberFreeScrollState()
    val width = remember { mutableStateOf(500.dp) }
    val height = remember { mutableStateOf(700.dp) }

    LaunchedEffect(
        key1 = noteScrollState.verticalScrollState.canScrollBackward || noteScrollState.verticalScrollState.isScrollInProgress,
        key2 = noteScrollState.horizontalScrollState.canScrollBackward || noteScrollState.horizontalScrollState.isScrollInProgress
    ) {
        if (!noteScrollState.verticalScrollState.canScrollBackward) {
            width.value += 88.dp
        }
        if (!noteScrollState.horizontalScrollState.canScrollBackward) {
            height.value += 176.dp
        }
    }

    Box(
        modifier
            .size(width.value, height.value)
            .freeScroll(noteScrollState)
    ) {

        Box(
            Modifier
                .width(width.value)
                .height(height.value)
        ) {

            Row(Modifier.fillMaxSize()) {
                repeat((width.value / 84.dp).toInt()) {
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
                repeat((height.value / 84.dp).toInt()) {
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
}