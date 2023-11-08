package ru.tanexc.notegraph.presentation.util

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import ru.tanexc.notegraph.R

class AppBarState {
    private val _currentAppBarParams: MutableState<AppBarParams> = mutableStateOf(AppBarParams.default())
    val params: AppBarParams by _currentAppBarParams

    fun updateTopAppBar(
        title: (@Composable () -> Unit) = params.title,
        navigationIcon: (@Composable () -> Unit) = params.navigationIcon,
        actions: @Composable (RowScope.() -> Unit) = params.actions,
        visible: Boolean = params.visible,
        borderEnabled: Boolean = params.borderEnabled
    ) {
        _currentAppBarParams.value = _currentAppBarParams.value.copy(
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            visible = visible,
            borderEnabled = borderEnabled
        )
    }
}

data class AppBarParams(
    val title: @Composable () -> Unit = {},
    val navigationIcon: @Composable () -> Unit = {},
    val actions: @Composable (RowScope.() -> Unit) = {},
    val visible: Boolean = true,
    val borderEnabled: Boolean = true
) {
    companion object {
        fun default() = AppBarParams(
            title = { Text(stringResource(R.string.app_name)) },
            navigationIcon = {},
            actions = {},
            visible = true,
            borderEnabled = true
        )
    }
}

val LocalAppBarState = compositionLocalOf { AppBarState() }

@Composable
fun rememberAppBarState(
    title: @Composable () -> Unit = LocalAppBarState.current.params.title,
    navigationIcon: @Composable () -> Unit = LocalAppBarState.current.params.navigationIcon,
    actions: @Composable (RowScope.() -> Unit) = LocalAppBarState.current.params.actions,
    visible: Boolean = LocalAppBarState.current.params.visible,
    borderEnabled: Boolean = LocalAppBarState.current.params.borderEnabled
): ProvidableCompositionLocal<AppBarState> {
    LocalAppBarState.current.updateTopAppBar(
        title,
        navigationIcon,
        actions,
        visible,
        borderEnabled
    )
    return remember { LocalAppBarState }
}