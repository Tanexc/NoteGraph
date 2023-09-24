package ru.tanexc.notegraph.core.util

import android.os.Parcelable
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Screen(
    val label: Int? = null,
    @IgnoredOnParcel
    val iconOutlined: ImageVector? = null,
    @IgnoredOnParcel
    val iconFilled: ImageVector? = null
): Parcelable {

    data object Notes : Screen()

    data object Settings : Screen()

    data object Login : Screen()
}