package ru.tanexc.notegraph.presentation.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.R

@Composable
fun AuthScreen(
    modifier: Modifier
) {
    val viewModel: AuthViewModel = hiltViewModel()

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = viewModel.login,
                onValueChange = { viewModel.updateLogin(it) },
                label = { Text(stringResource(R.string.login)) }
            )

            OutlinedTextField(
                value = viewModel.login,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation()
            )

            Text(
                text = stringResource(R.string.forgot_password),
                color = ,
                textAlign = TextAlign.End,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.size(48.dp))

            Button(onClick = { viewModel.signIn() }) {
                Text(stringResource(R.string.enter))
            }

        }
    }

}