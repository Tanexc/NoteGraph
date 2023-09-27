package ru.tanexc.notegraph.presentation.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.presentation.util.AuthOption
import ru.tanexc.notegraph.presentation.util.LocalSettingsProvider

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

    when (authOption) {
        AuthOption.WELCOME -> {
            WelocmeScreen(
                colorScheme = colorScheme,
                onSignUp = { authOption = AuthOption.SIGN_UP },
                onSignIn = { authOption = AuthOption.SIGN_IN },
                onAuthAsGuest = { viewModel.signInAsGuest() }
            )
        }

        AuthOption.SIGN_IN -> {
            SignInScreen(
                colorScheme = colorScheme,
                onSubmit = { email, password ->
                    viewModel.signIn(email, password)
                }
            )
        }

        AuthOption.SIGN_UP -> {
            SignUpScreen(
                colorScheme = colorScheme,
                onSubmit = { email, password, name ->
                    viewModel.signUp(email, password, name)
                }
            )
        }
    }


}