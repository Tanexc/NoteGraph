package ru.tanexc.notegraph.presentation.util

import androidx.compose.runtime.compositionLocalOf
import ru.tanexc.notegraph.domain.model.settings.Settings

val LocalSettingsProvider = compositionLocalOf<Settings> { error("Settings not presented") }