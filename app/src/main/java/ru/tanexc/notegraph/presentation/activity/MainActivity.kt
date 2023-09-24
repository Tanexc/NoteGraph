package ru.tanexc.notegraph.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.tanexc.notegraph.presentation.screen.main.MainViewModel
import ru.tanexc.notegraph.presentation.screen.main.NoteGraphApp
import ru.tanexc.notegraph.presentation.util.LocalSettingsProvider

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)



        setContent {
            CompositionLocalProvider {
                TODO("LocalSettingsProvider")
            }

            NoteGraphApp(viewModel)
        }
    }
}