package ru.tanexc.notegraph.presentation.screen.settings.view_model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Portrait
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.presentation.ui.shapes.firstPreferenceShape
import ru.tanexc.notegraph.presentation.ui.shapes.lastPreferenceShape
import ru.tanexc.notegraph.presentation.ui.shapes.middlePreferenceShape
import ru.tanexc.notegraph.presentation.ui.theme.Typography
import ru.tanexc.notegraph.presentation.ui.widgets.cards.PreferenceCard
import ru.tanexc.notegraph.presentation.util.LocalSettingsProvider

@Composable
fun SettingsScreen(
    modifier: Modifier
) {
    val colorScheme = rememberColorScheme(
        isDarkTheme = LocalSettingsProvider.current.isDarkMode,
        amoledMode = LocalSettingsProvider.current.amoledMode,
        colorTuple = rememberDynamicThemeState().colorTuple.value
    )
    val settings = LocalSettingsProvider.current

    LazyColumn(modifier, contentPadding = PaddingValues(22.dp)) {
        item {
            PreferenceCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.secondaryContainer),
                borderEnabled = settings.bordersEnabled,
                borderColor = colorScheme.outline,
                shape = firstPreferenceShape(),
                header = {
                    Icon(
                        imageVector = Icons.Filled.Style,
                        contentDescription = null,
                        modifier = Modifier.align(CenterVertically)

                    )
                    Spacer(modifier = Modifier.size(22.dp))
                    Text(
                        text = stringResource(R.string.style),
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically),
                        style = Typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold)
                    )
                }
            ) {
                Row {
                    Text(
                        stringResource(R.string.amoled_mode),
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                    Switch(
                        checked = true,
                        onCheckedChange = {},
                        modifier = Modifier
                            .align(CenterVertically)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colorScheme.outline.copy(0.2f), CircleShape)
                )

                Row {
                    Text(
                        stringResource(R.string.dynamic_theme),
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                    Switch(
                        checked = true,
                        onCheckedChange = {},
                        modifier = Modifier
                            .align(CenterVertically)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colorScheme.outline.copy(0.2f), CircleShape)
                )

                Row {
                    Text(
                        stringResource(R.string.outline), modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                    Switch(
                        checked = true,
                        onCheckedChange = {},
                        modifier = Modifier
                            .align(CenterVertically)
                    )
                }


            }
            Spacer(modifier = Modifier.size(4.dp))
        }
        item {
            PreferenceCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.secondaryContainer),
                borderEnabled = settings.bordersEnabled,
                borderColor = colorScheme.outline,
                shape = middlePreferenceShape(),
                header = {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.align(CenterVertically)

                    )
                    Spacer(modifier = Modifier.size(22.dp))
                    Text(
                        stringResource(R.string.profile),
                        Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                }
            ) {
                Row {
                    Text(
                        stringResource(R.string.delete_profile),
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .align(CenterVertically)
                    ) {
                        Icon(Icons.Outlined.Block, null)
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colorScheme.outline.copy(0.2f), CircleShape)
                )

                Row {
                    Text(
                        stringResource(R.string.change_password),
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .align(CenterVertically)
                    ) {
                        Icon(Icons.Outlined.Edit, null)
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colorScheme.outline.copy(0.2f), CircleShape)
                )

                Row {
                    Text(
                        stringResource(R.string.delete_notes),
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .align(CenterVertically)
                    ) {
                        Icon(Icons.Outlined.Delete, null)
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colorScheme.outline.copy(0.2f), CircleShape)
                )

                Row {
                    Text(
                        stringResource(R.string.update_photo),
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .align(CenterVertically)
                    ) {
                        Icon(Icons.Outlined.Portrait, null)
                    }
                }
            }
            Spacer(modifier = Modifier.size(4.dp))
        }
        item {
            PreferenceCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.secondaryContainer),
                borderEnabled = settings.bordersEnabled,
                borderColor = colorScheme.outline,
                shape = lastPreferenceShape(),
                header = {
                    Icon(
                        imageVector = Icons.Outlined.Code,
                        contentDescription = null,
                        modifier = Modifier.align(CenterVertically)

                    )
                    Spacer(modifier = Modifier.size(22.dp))
                    Text(
                        stringResource(R.string.about_app),
                        Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                }
            ) {

            }
        }
    }
}