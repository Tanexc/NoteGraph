package ru.tanexc.notegraph.domain.model.settings

import androidx.compose.ui.graphics.Color
import com.t8rin.dynamic.theme.ColorTuple

data class Settings(
    val amoledMode: Boolean,
    val isDarkMode: Boolean,
    val useDynamicColor: Boolean,
    val bordersEnabled: Boolean,
    val colorTuple: ColorTuple
) {

    companion object {
        fun Default() = Settings(
            amoledMode = false,
            isDarkMode = true,
            bordersEnabled = true,
            useDynamicColor = false,
            colorTuple = ColorTuple(Color.Unspecified)
        )
    }
}