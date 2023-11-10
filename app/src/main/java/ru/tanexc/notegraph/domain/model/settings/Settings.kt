package ru.tanexc.notegraph.domain.model.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.t8rin.dynamic.theme.ColorTuple
import com.t8rin.dynamic.theme.getAppColorTuple
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState

data class Settings(
    val amoledMode: Boolean,
    val isDarkMode: Boolean,
    val useDynamicColor: Boolean,
    val bordersEnabled: Boolean,
    val colorTuple: ColorTuple,
    val headerLines: Int
) {

    companion object {
        fun Default() = Settings(
            amoledMode = false,
            isDarkMode = true,
            bordersEnabled = true,
            useDynamicColor = false,
            colorTuple = ColorTuple(Color.Unspecified),
            headerLines = 1
        )
    }

    @Composable
    fun getColorScheme() =
        rememberColorScheme(
            isDarkTheme = isDarkMode,
            amoledMode = amoledMode,
            colorTuple = getAppColorTuple(
                defaultColorTuple = colorTuple,
                dynamicColor = useDynamicColor,
                darkTheme = isDarkMode
            )
        )
}