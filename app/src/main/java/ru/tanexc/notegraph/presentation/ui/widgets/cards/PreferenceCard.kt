package ru.tanexc.notegraph.presentation.ui.widgets.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun PreferenceCard(
    modifier: Modifier = Modifier,
    borderEnabled: Boolean,
    borderColor: Color = Color.Transparent,
    shape: Shape,
    header: @Composable RowScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    var collapsed: Boolean by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .clip(shape)
            .border(1.dp, borderColor.copy(if (borderEnabled) 0.7f else 0f), shape)
            .then(modifier)
            .animateContentSize()
    ) {

        Row(modifier = Modifier
            .clip(shape)
            .clickable { collapsed = !collapsed }
            .padding(22.dp)
        ) {
            header()
            Spacer(modifier = Modifier.size(22.dp))
            IconButton(
                onClick = { collapsed = !collapsed }
            ) {
                val animatedFloat = animateFloatAsState(
                    targetValue = if (collapsed) 1f else -1f,
                    label = ""
                )
                Icon(
                    modifier = Modifier.scale(1f, animatedFloat.value),
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }

        AnimatedVisibility(visible = !collapsed) {
            Column(
                modifier = Modifier.padding(22.dp, 0.dp, 22.dp, 22.dp),
                content = content
            )
        }

    }
}