package ru.tanexc.notegraph.presentation.ui.widgets.action_button

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.presentation.util.FabSize

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MultipleFloatingActionButton(
    modifier: Modifier = Modifier,
    icon: @Composable (iconSize: Dp) -> Unit,
    text: @Composable () -> Unit = {},
    expanded: Boolean = false,
    options: List<FabOption>,
    scrimEnabled: Boolean = true,
    colors: FabWithOptionsColors = FabWithOptionsColors.make(),
    scrimColor: Color = MaterialTheme.colorScheme.scrim.copy(0.32f),
    globalOptionsGravity: OptionsGravity = OptionsGravity.End,
    internalOptionsGravity: OptionsGravity = OptionsGravity.End,
    onOptionSelected: (option: FabOption) -> Unit,
    onClick: (showingOptions: Boolean) -> Unit = {}
) {
    var isShowingOptions by rememberSaveable { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (isShowingOptions) 1f else 0f, label = "")

    val horizontalAlignment = when (globalOptionsGravity) {
        OptionsGravity.Start -> Alignment.Start
        OptionsGravity.Center -> Alignment.CenterHorizontally
        OptionsGravity.End -> Alignment.End
    }
    val crossAxisAlignment = when (internalOptionsGravity) {
        OptionsGravity.Start -> FlowCrossAxisAlignment.Start
        OptionsGravity.Center -> FlowCrossAxisAlignment.Center
        OptionsGravity.End -> FlowCrossAxisAlignment.End
    }

    if (scrimEnabled) {
        Scrim(
            showing = isShowingOptions,
            onTap = { isShowingOptions = false },
            color = scrimColor
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        AnimatedVisibility(
            visible = isShowingOptions,
            modifier = Modifier.weight(1f, false),
            enter = fadeIn() + slideInVertically { it / 3 },
            exit = fadeOut() + slideOutVertically { it / 3 }
        ) {
            FlowColumn(
                crossAxisAlignment = crossAxisAlignment,
                crossAxisSpacing = 4.dp,
                mainAxisSpacing = 2.dp,
                modifier = Modifier.wrapContentWidth()
            ) {
                options.forEachIndexed { index, option ->
                    ExpandableFloatingActionButton(
                        modifier = Modifier
                            .scale(scale)
                            .align(horizontalAlignment),
                        size = FabSize.Small,
                        icon = {
                            Icon(
                                imageVector = option.icon,
                                contentDescription = null,
                                modifier = Modifier.size(it)
                            )
                        },
                        onClick = {
                            onOptionSelected(option.copy(index = index))
                            isShowingOptions = false
                        },
                        contentColor = colors.optionButtonContentColor,
                        containerColor = colors.optionButtonContainerColor
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))
            }

        }
        ExpandableFloatingActionButton(
            onClick = {
                isShowingOptions = !isShowingOptions
                onClick(isShowingOptions)
            },
            expanded = expanded,
            icon = { size ->
                AnimatedContent(targetState = isShowingOptions, label = "") { showClose ->
                    if (showClose) Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        modifier = Modifier.size(size)
                    )
                    else icon(size)
                }
            },
            text = {
                if (text != {}) {
                    AnimatedContent(targetState = isShowingOptions, label = "") { showClose ->
                        if (showClose) Text(stringResource(R.string.close))
                        else text()
                    }
                }
            },
            contentColor = colors.contentColor,
            containerColor = colors.containerColor
        )

    }
}

interface FabWithOptionsColors {
    val contentColor: Color
    val containerColor: Color
    val optionButtonContentColor: Color
    val optionButtonContainerColor: Color

    companion object {
        @Composable
        fun make(
            containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
            contentColor: Color = contentColorFor(containerColor),
            optionButtonContainerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
            optionButtonContentColor: Color = contentColorFor(optionButtonContainerColor),
        ): FabWithOptionsColors = object : FabWithOptionsColors {
            override val contentColor: Color = contentColor
            override val containerColor: Color = containerColor
            override val optionButtonContentColor: Color = optionButtonContentColor
            override val optionButtonContainerColor: Color = optionButtonContainerColor
        }
    }

}


data class FabOption(
    val icon: ImageVector,
    val index: Int = 0,
)

enum class OptionsGravity { Start, Center, End }

@Composable
fun Scrim(
    showing: Boolean,
    onTap: () -> Unit,
    fraction: () -> Float = { 0.5f },
    color: Color = MaterialTheme.colorScheme.scrim
) {
    AnimatedVisibility(
        visible = showing,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onTap()
                }
        ) {
            drawRect(color = color, alpha = fraction())
        }
    }
}