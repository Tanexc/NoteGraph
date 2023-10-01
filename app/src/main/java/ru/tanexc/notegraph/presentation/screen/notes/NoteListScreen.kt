package ru.tanexc.notegraph.presentation.screen.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.domain.model.Note
import ru.tanexc.notegraph.presentation.screen.notes.view_model.NoteListViewModel
import ru.tanexc.notegraph.presentation.ui.widgets.ItemCard
import ru.tanexc.notegraph.presentation.util.LocalSettingsProvider
import ru.tanexc.notegraph.presentation.util.rememberAppBarState

@Composable
fun NoteListScreen(
    modifier: Modifier,
    onOpenNote: (Note) -> Unit
) {
    val viewModel: NoteListViewModel = hiltViewModel()
    val topAppBarState = rememberAppBarState()
    val colorScheme = rememberColorScheme(
        isDarkTheme = LocalSettingsProvider.current.isDarkMode,
        amoledMode = LocalSettingsProvider.current.amoledMode,
        colorTuple = rememberDynamicThemeState().colorTuple.value
    )

    topAppBarState.current.updateTopAppBar(
        title = { Text(stringResource(R.string.notes)) },

    )

    Box(modifier.fillMaxSize()) {

        when (viewModel.noteList) {
            null -> {
                Text(stringResource(R.string.error_loading), modifier = Modifier.align(Alignment.Center))
            }

            else -> {
                viewModel.noteList?.let {
                    LazyColumn(Modifier.fillMaxSize()) {
                        items(viewModel.noteList?: emptyList()) {
                            Spacer(modifier = Modifier.size(16.dp))
                            ItemCard(
                                modifier = Modifier.clickable { onOpenNote(it) },
                                borderEnabled = LocalSettingsProvider.current.bordersEnabled,
                                borderColor = colorScheme.outline,
                                backgroundColor = colorScheme.secondary
                            ) {
                                
                                Text(it.label, modifier = Modifier.align(Alignment.Center))

                            }
                        }
                    }
                }?: CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
    
    FloatingActionButton(
        onClick = { onOpenNote(Note.Empty()) }
    ) {
        Icon(Icons.Outlined.Create, null)
    }

}