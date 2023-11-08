package ru.tanexc.notegraph.presentation.ui.shapes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

fun firstPreferenceShape(): Shape = RoundedCornerShape(22.dp, 22.dp, 4.dp, 4.dp)

fun lastPreferenceShape(): Shape = RoundedCornerShape(4.dp, 4.dp, 22.dp, 22.dp)

fun middlePreferenceShape(): Shape = RoundedCornerShape(4.dp)
