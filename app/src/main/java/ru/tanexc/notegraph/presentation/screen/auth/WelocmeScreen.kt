package ru.tanexc.notegraph.presentation.screen.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoAccounts
import androidx.compose.material.icons.outlined.HowToReg
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.presentation.util.rememberAppBarState

@Composable
fun WelocmeScreen(
    colorScheme: ColorScheme,
    onSignUp: () -> Unit,
    onSignIn: () -> Unit,
    onAuthAsGuest: () -> Unit
) {

    var authAsGuest: Boolean by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {

            FilledIconButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onSignIn() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Person, null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(stringResource(R.string.enter))
                }
            }


            FilledIconButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onSignUp() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.HowToReg, null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(stringResource(R.string.sign_up))
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(colorScheme.outline)
                        .weight(0.5f)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(stringResource(R.string.or))
                Spacer(modifier = Modifier.size(8.dp))
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(colorScheme.outline)
                        .weight(0.5f)
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            AnimatedContent(authAsGuest, label = "") {
                if (!it) {
                    OutlinedIconButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            authAsGuest = true
                            onAuthAsGuest()
                        }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.NoAccounts, null)
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(stringResource(R.string.auth_as_guest))
                        }
                    }
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(32.dp)
                            .align(CenterHorizontally)
                    )
                }
            }


        }
    }

}