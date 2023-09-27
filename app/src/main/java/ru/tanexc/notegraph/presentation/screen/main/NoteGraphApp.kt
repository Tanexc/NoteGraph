package ru.tanexc.notegraph.presentation.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.NavHost
import dev.olshevski.navigation.reimagined.rememberNavController
import ru.tanexc.notegraph.core.util.Screen
import ru.tanexc.notegraph.presentation.screen.auth.AuthScreen
import ru.tanexc.notegraph.presentation.screen.notes.NoteScreen
import ru.tanexc.notegraph.presentation.ui.theme.NoteGraphTheme
import ru.tanexc.notegraph.presentation.ui.widgets.TopAppBar
import ru.tanexc.notegraph.presentation.util.AppBarParams
import ru.tanexc.notegraph.presentation.util.LocalSettingsProvider
import ru.tanexc.notegraph.presentation.util.rememberAppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteGraphApp(
    viewModel: MainViewModel
) {
    val navController: NavController<Screen> = rememberNavController(startDestination = viewModel.currentScreen)
    val topAppBarState = rememberAppBarState()
    val colorScheme = rememberColorScheme(
        isDarkTheme = LocalSettingsProvider.current.isDarkMode,
        amoledMode = LocalSettingsProvider.current.amoledMode,
        colorTuple = rememberDynamicThemeState().colorTuple.value,
    )

    NoteGraphTheme {
        Scaffold(
            topBar = {
                TopAppBar(topAppBarState = topAppBarState.current, outlineColor = colorScheme.outline)
            },
        ) { innerPaddings ->
            NavBackHandler(navController)
            NavHost(navController) { screen ->
                when (screen) {
                    Screen.Login -> AuthScreen(
                        modifier = Modifier
                            .padding(innerPaddings)
                    )

                    Screen.Notes -> NoteScreen(
                        modifier = Modifier
                            .padding(innerPaddings)
                    )

                    else -> {}
                }

            }

        }

    }

}