package ru.tanexc.notegraph.presentation.ui.shapes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

fun Modifier.firstPreferenceShape(): Modifier = this.clip(RoundedCornerShape(22.dp, 22.dp, 4.dp, 4.dp))

fun Modifier.lastPreferenceShape(): Modifier = this.clip(RoundedCornerShape(4.dp, 4.dp, 22.dp, 22.dp))

fun Modifier.middlePreferenceShape(): Modifier = this.clip(RoundedCornerShape(4.dp))
