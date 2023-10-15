package ru.tanexc.notegraph.presentation.screen.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.NavHost
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.popAll
import dev.olshevski.navigation.reimagined.rememberNavController
import kotlinx.coroutines.launch
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.core.util.Screen
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.presentation.screen.auth.AuthScreen
import ru.tanexc.notegraph.presentation.screen.notes_list.NoteListScreen
import ru.tanexc.notegraph.presentation.screen.note.NoteScreen
import ru.tanexc.notegraph.presentation.ui.theme.NoteGraphTheme
import ru.tanexc.notegraph.presentation.ui.theme.Typography
import ru.tanexc.notegraph.presentation.ui.widgets.app_bars.TopAppBar
import ru.tanexc.notegraph.presentation.util.LocalSettingsProvider
import ru.tanexc.notegraph.presentation.util.rememberAppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteGraphApp(
    viewModel: MainViewModel
) {
    val activity: Activity = LocalContext.current as Activity
    val navController: NavController<Screen> =
        rememberNavController(startDestination = viewModel.currentScreen)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val topAppBarState = rememberAppBarState()
    val colorScheme = rememberColorScheme(
        isDarkTheme = LocalSettingsProvider.current.isDarkMode,
        amoledMode = LocalSettingsProvider.current.amoledMode,
        colorTuple = rememberDynamicThemeState().colorTuple.value,
    )

    var selectedNote: Note? by remember { mutableStateOf(null) }

    LaunchedEffect(viewModel.currentScreen) {
        navController.popAll()
        navController.navigate(viewModel.currentScreen)
        drawerState.close()
    }

    NoteGraphTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Spacer(Modifier.size(64.dp))
                        Text(viewModel.user?.name ?: "null")
                        Spacer(Modifier.size(64.dp))
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Outlined.NoteAlt, null) },
                            label = { Text(stringResource(R.string.notes)) },
                            selected = viewModel.currentScreen is Screen.Notes,
                            onClick = { viewModel.updateCurrentScreen(Screen.Notes) },
                            shape = RoundedCornerShape(0, 50, 50, 0)
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Outlined.Settings, null) },
                            label = { Text(stringResource(R.string.settings)) },
                            selected = viewModel.currentScreen is Screen.Settings,
                            onClick = { viewModel.updateCurrentScreen(Screen.Settings) },
                            shape = RoundedCornerShape(0, 50, 50, 0)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = {
                            viewModel.signOut()
                        }) {
                            Text(
                                stringResource(R.string.sign_out),
                                color = colorScheme.tertiary.copy(0.5f)
                            )
                        }
                        Text(
                            text = stringResource(R.string.app_version),
                            color = contentColorFor(colorScheme.surface).copy(0.5f),
                            style = Typography.labelSmall
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        topAppBarState = topAppBarState,
                        outlineColor = colorScheme.outline
                    )
                },
            ) { innerPaddings ->
                NavBackHandler(navController)
                NavHost(navController) { screen ->
                    when (screen) {
                        Screen.Login -> {
                            AuthScreen(
                                modifier = Modifier
                                    .padding(innerPaddings)
                            )
                        }

                        Screen.Notes -> {
                            topAppBarState.current.updateTopAppBar(
                                title = { Text(stringResource(R.string.notes)) },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(Icons.Outlined.Menu, null)
                                    }
                                }
                            )
                            NoteListScreen(
                                modifier = Modifier.padding(innerPaddings),
                                onOpenNote = {
                                    selectedNote = it
                                    viewModel.updateCurrentScreen(Screen.Note)
                                }
                            )

                        }

                        Screen.Settings -> {
                            topAppBarState.current.updateTopAppBar(
                                title = { Text(stringResource(R.string.settings)) },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(Icons.Outlined.Menu, null)
                                    }
                                }
                            )
                        }

                        Screen.Note -> {
                            selectedNote?.let { note ->
                                topAppBarState.current.updateTopAppBar(
                                    navigationIcon = {
                                        IconButton(onClick = { scope.launch { viewModel.updateCurrentScreen(Screen.Notes) } }) {
                                            Icon(Icons.AutoMirrored.Outlined.ArrowBack, null)
                                        }
                                    }
                                )
                                NoteScreen(
                                    modifier = Modifier
                                        .padding(innerPaddings),
                                    note = note
                                )
                            }
                        }
                    }

                }

            }
        }

    }

    BackHandler(enabled = true) {
        activity.finish()
    }

}