package ru.tanexc.notegraph.presentation.screen.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.domain.model.note.Note
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
        when (!viewModel.loading) {
            true -> viewModel.noteList?.let {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(viewModel.noteList ?: emptyList()) {
                        Spacer(modifier = Modifier.size(16.dp))
                        ItemCard(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { onOpenNote(it) }
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            borderEnabled = LocalSettingsProvider.current.bordersEnabled,
                            borderColor = colorScheme.outline,
                            backgroundColor = colorScheme.secondaryContainer
                        ) {
                            Box(Modifier.padding(16.dp)) {
                                Spacer(modifier = Modifier.size(56.dp))

                                Text(it.label, modifier = Modifier.align(Alignment.Center))

                                Spacer(modifier = Modifier.size(56.dp))
                            }
                        }
                    }
                }
            } ?: Text(
                stringResource(R.string.error_loading),
                modifier = Modifier.align(Alignment.Center)
            )

            false -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            onClick = { onOpenNote(Note.Empty()) }
        ) {
            Icon(Icons.Outlined.Create, null)
        }

    }



}