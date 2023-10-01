package ru.tanexc.notegraph.presentation.screen.auth

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.core.util.AuthOption
import ru.tanexc.notegraph.presentation.util.LocalSettingsProvider
import ru.tanexc.notegraph.presentation.util.rememberAppBarState

@Composable
fun AuthScreen(
    modifier: Modifier
) {
    val viewModel: AuthViewModel = hiltViewModel()
    val dynamicThemeState = rememberDynamicThemeState()
    val colorScheme = rememberColorScheme(
        isDarkTheme = LocalSettingsProvider.current.isDarkMode,
        amoledMode = LocalSettingsProvider.current.amoledMode,
        colorTuple = dynamicThemeState.colorTuple.value,
    )
    var authOption: AuthOption by remember { mutableStateOf(AuthOption.WELCOME) }
    val topAppBarState = rememberAppBarState()

    when (authOption) {
        AuthOption.WELCOME -> {
            topAppBarState.current.updateTopAppBar(title = { Text(stringResource(R.string.app_name)) })
            WelocmeScreen(
                colorScheme = colorScheme,
                onSignUp = { authOption = AuthOption.SIGN_UP },
                onSignIn = { authOption = AuthOption.SIGN_IN },
                onAuthAsGuest = { viewModel.signInAsGuest() }
            )
        }

        AuthOption.SIGN_IN -> {
            topAppBarState.current.updateTopAppBar(
                title = { Text(stringResource(R.string.enter)) },
                navigationIcon = {
                    IconButton(onClick = { authOption = AuthOption.WELCOME }) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack, null
                        )
                    }
                }
            )
            SignInScreen(
                colorScheme = colorScheme,
                onSubmit = { email, password ->
                    viewModel.signIn(email, password)
                }
            )
        }

        AuthOption.SIGN_UP -> {
            topAppBarState.current.updateTopAppBar(
                title = { Text(stringResource(R.string.sign_up)) },
                navigationIcon = {
                    IconButton(onClick = { authOption = AuthOption.WELCOME }) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack, null
                        )
                    }
                }
            )
            SignUpScreen(
                colorScheme = colorScheme,
                onSubmit = { email, password, name ->
                    viewModel.signUp(email, password, name)
                }
            )
        }
    }


}