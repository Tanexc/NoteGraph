package ru.tanexc.notegraph.presentation.ui.widgets

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.gigamole.composeshadowsplus.softlayer.softLayerShadow
import ru.tanexc.notegraph.presentation.util.AppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    topAppBarState: AppBarState,
    outlineColor: Color = Color.Transparent
) {
    CenterAlignedTopAppBar(
        topAppBarState.params.title,
        if (topAppBarState.params.borderEnabled) {
            modifier.drawWithContent {
                drawContent()
                drawRect(
                    color = outlineColor,
                    topLeft = Offset(0f, this.size.height),
                    size = Size(this.size.width, density)
                )
            }
        } else {
            modifier.softLayerShadow(spread = 2.dp, offset = DpOffset(2.dp, 0.dp))
        },
        topAppBarState.params.navigationIcon,
        topAppBarState.params.actions,
        windowInsets,
        colors,
        scrollBehavior
    )
}