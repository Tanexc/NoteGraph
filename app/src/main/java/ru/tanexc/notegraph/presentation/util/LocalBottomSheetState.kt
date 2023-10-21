package ru.tanexc.notegraph.presentation.util

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


class BottomSheetState {
    private val _content: MutableState<(@Composable ColumnScope.() -> Unit)> = mutableStateOf({ })
    val content by _content

    private val _visibility: MutableState<Boolean> = mutableStateOf(false)
    val visibility by _visibility

    fun disable() {
        _visibility.value = false
    }

    fun setContent(block: @Composable ColumnScope.() -> Unit) {
        _content.value = block
        _visibility.value = true
    }
}

val LocalBottomSheetState = compositionLocalOf { BottomSheetState() }

@Composable
fun rememberBottomSheetState(): ProvidableCompositionLocal<BottomSheetState> {
    return remember { LocalBottomSheetState }
}