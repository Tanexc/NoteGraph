package ru.tanexc.notegraph.presentation.screen.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.core.util.isEmailValid
import ru.tanexc.notegraph.core.util.isPasswordValid

@Composable
fun SignUpScreen(
    colorScheme: ColorScheme,
    onSubmit: (email: String, password: String, name: String) -> Unit
) {

    var email: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var repeatPassword: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }

    var emailTextFieldFocused: Boolean by remember { mutableStateOf(false) }
    var passwordTextFieldFocused: Boolean by remember { mutableStateOf(false) }

    var showPassword: Boolean by remember { mutableStateOf(false) }
    var signUp: Boolean by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.name)) }
            )

            AnimatedVisibility(
                visible = name != "",
                enter = expandVertically { it },
                exit = shrinkVertically { it }) {

                Column {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(R.string.email)) },
                        isError = !(isEmailValid(email) || emailTextFieldFocused || email == ""),
                        supportingText = if (!(isEmailValid(email) || emailTextFieldFocused || email == "")) {
                            { Text(stringResource(R.string.email_invalid)) }
                        } else null,
                        modifier = Modifier.onFocusChanged {
                            emailTextFieldFocused = it.isFocused
                        }
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(R.string.password)) },
                        isError = !(isPasswordValid(password) || passwordTextFieldFocused || password == ""),
                        supportingText =
                        if (!(isPasswordValid(password) || passwordTextFieldFocused || password == "")) {
                            { Text(stringResource(R.string.password_invalid)) }
                        } else null,
                        modifier = Modifier.onFocusChanged {
                            passwordTextFieldFocused = it.isFocused
                        },
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                if (showPassword) {
                                    Icon(Icons.Outlined.VisibilityOff, null)
                                } else {
                                    Icon(Icons.Outlined.Visibility, null)
                                }
                            }
                        },
                        visualTransformation = if (showPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        }
                    )

                    OutlinedTextField(
                        value = repeatPassword,
                        onValueChange = { repeatPassword = it },
                        label = { Text(stringResource(R.string.repeat_password)) },
                        isError = !(password == repeatPassword || repeatPassword == ""),
                        supportingText =
                        if (!(password == repeatPassword || repeatPassword == "")) {
                            { Text(stringResource(R.string.passwords_dont_match)) }
                        } else null,
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                if (showPassword) {
                                    Icon(Icons.Outlined.VisibilityOff, null)
                                } else {
                                    Icon(Icons.Outlined.Visibility, null)
                                }
                            }
                        },
                        visualTransformation = if (showPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.size(32.dp))

            if (!signUp) {
                Button(
                    enabled = isEmailValid(email) && isPasswordValid(password) && password == repeatPassword,
                    onClick = {
                        signUp = true
                        onSubmit(email, password, name)
                    }
                ) {
                    Text(stringResource(R.string.enter))
                }
            } else {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                }

            }

        }
    }
}