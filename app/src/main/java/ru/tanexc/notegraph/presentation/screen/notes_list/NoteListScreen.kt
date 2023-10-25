package ru.tanexc.notegraph.presentation.screen.notes_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.presentation.screen.notes_list.view_model.NoteListViewModel
import ru.tanexc.notegraph.presentation.ui.theme.Typography
import ru.tanexc.notegraph.presentation.ui.widgets.cards.ImagePieceNoteCard
import ru.tanexc.notegraph.presentation.ui.widgets.cards.ItemCard
import ru.tanexc.notegraph.presentation.ui.widgets.cards.TextPieceNoteCard
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
        actions = {
            when (viewModel.synchronizing) {
                is Action.Loading -> CircularProgressIndicator(Modifier.size(24.dp))
                is Action.Error -> IconButton(
                    onClick = { }
                ) {
                    Icon(
                        Icons.Filled.ReportProblem,
                        null
                    )
                }

                else -> {}
            }
        }
    )

    Box(modifier.fillMaxSize()) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.noteList) { note ->
                val textPiece = note.textPieces.getOrNull(0)
                val imagePiece = note.imagePieces.getOrNull(0)


                ItemCard(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onOpenNote(note) }
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    borderEnabled = LocalSettingsProvider.current.bordersEnabled,
                    borderColor = colorScheme.outline,
                    backgroundColor = colorScheme.secondaryContainer) {
                    Column {
                        Column(
                            Modifier.border(
                                width = 1.dp,
                                shape = RoundedCornerShape(16.dp),
                                brush = if (LocalSettingsProvider.current.bordersEnabled) SolidColor(
                                    colorScheme.outline.copy(0.7f)
                                )
                                else SolidColor(Color.Transparent)
                            )
                        ) {
                            textPiece?.let {
                                TextPieceNoteCard(
                                    textPiece = it,
                                    colorScheme = colorScheme
                                )
                            } ?: imagePiece?.let {
                                ImagePieceNoteCard(
                                    imagePiece = it,
                                    colorScheme = colorScheme
                                )
                            } ?: Text(
                                modifier = Modifier
                                    .padding(16.dp, 48.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = colorScheme.contentColorFor(colorScheme.secondaryContainer)
                                    .copy(0.6f),
                                text = stringResource(R.string.empty_note)
                            )
                        }

                        if (note.label != "") {
                            Text(
                                text = note.label,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                style = Typography.headlineSmall,
                                maxLines = 1,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                color = colorScheme.contentColorFor(colorScheme.secondaryContainer)
                            )
                        }
                    }
                }
            }


        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { viewModel.createNote(onOpenNote) }
        ) {
            Icon(Icons.Outlined.Create, null)
        }
    }
}
