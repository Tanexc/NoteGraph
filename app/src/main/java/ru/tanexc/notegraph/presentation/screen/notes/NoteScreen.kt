package ru.tanexc.notegraph.presentation.screen.notes

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.presentation.screen.notes.components.ImagePieceSheetContent
import ru.tanexc.notegraph.presentation.screen.notes.components.NoteSheetContent
import ru.tanexc.notegraph.presentation.screen.notes.view_model.NoteViewModel
import ru.tanexc.notegraph.presentation.ui.widgets.FabOption
import ru.tanexc.notegraph.presentation.ui.widgets.MultipleFloatingActionButton
import ru.tanexc.notegraph.presentation.ui.widgets.note.NoteField
import ru.tanexc.notegraph.presentation.util.rememberAppBarState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteScreen(
    modifier: Modifier,
    note: Note
) {
    val context = LocalContext.current
    val viewModel: NoteViewModel = hiltViewModel()
    var showNoteSheet: Boolean by remember { mutableStateOf(false) }
    var showImagePieceSheet: Boolean by remember { mutableStateOf(false) }
    var showTextPieceSheet: Boolean by remember { mutableStateOf(false) }
    val topAppBarState = rememberAppBarState()
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                val imageRequest = ImageRequest.Builder(context).data(it).build()
                viewModel.createImagePiece(imageRequest, context)
            }
        }
    )

    when (val currentNote = viewModel.note) {
        is Note -> {
            topAppBarState.current.updateTopAppBar(
                title = {
                    Text(currentNote.label, modifier = Modifier.basicMarquee())
                },
                actions = {
                    when (viewModel.synchronizing) {
                        is Action.Loading -> CircularProgressIndicator(Modifier.size(24.dp))
                        is Action.Error -> Icon(
                            Icons.Filled.ReportProblem,
                            null
                        )

                        else -> {}
                    }
                    IconButton(
                        enabled = viewModel.synchronizing !is Action.Loading,
                        onClick = {
                            showImagePieceSheet = false
                            showNoteSheet = !showNoteSheet
                        }
                    ) {
                        Icon(
                            if (showNoteSheet) Icons.Filled.Build else Icons.Outlined.Build,
                            null
                        )
                    }
                }
            )

            Box(modifier.fillMaxSize()) {
                NoteField(
                    modifier = Modifier.fillMaxSize(),
                    focusedPiece = viewModel.focusedPieceId,
                    imagePieces = currentNote.imagePieces,
                    textPieces = currentNote.textPieces,
                    onImagePieceMove = { viewModel.updateImagePiece(it) },
                    onTextPieceMove = { viewModel.updateTextPiece(it) },
                    onFocusedPieceChange = { viewModel.updateFocusedPiece(it) },
                    onReleaseImagePiece = { viewModel.saveImagePiece(it) },
                    onReleaseTextPiece = { viewModel.saveTextPiece(it) }
                )

                AnimatedVisibility(
                    visible = showImagePieceSheet || showNoteSheet || showTextPieceSheet,
                    enter = slideInVertically { it },
                    exit = slideOutVertically { it }
                ) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showNoteSheet = false
                            showImagePieceSheet = false
                        }
                    ) {

                        if (showNoteSheet) {
                            NoteSheetContent(
                                note = currentNote,
                                onValueChanged = { viewModel.updateNote(it) })
                        }

                        if (showImagePieceSheet) {
                            ImagePieceSheetContent(
                                piece = currentNote.imagePieces.first { it.documentId == viewModel.focusedPieceId },
                                onValueChanged = { piece ->
                                    viewModel.updateFocusedPiece(piece.documentId)
                                    viewModel.updateImagePiece(piece)
                                }
                            )
                        }


                    }
                }

                MultipleFloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    icon = {
                        Icon(
                            Icons.Outlined.Add,
                            null,
                            modifier = Modifier.size((it))
                        )
                    },
                    options = listOf(
                        FabOption(
                            icon = Icons.Outlined.Image,
                            index = 0
                        ),
                        FabOption(
                            icon = Icons.AutoMirrored.Outlined.Article,
                            index = 1
                        )
                    ),
                    onOptionSelected = {
                        when (it.index) {
                            0 -> {
                                imagePicker.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                                if (viewModel.focusedPieceId != null) {
                                    showImagePieceSheet = true
                                    showTextPieceSheet = false
                                }
                            }

                            1 -> {
                                viewModel.createTextPiece()
                                showImagePieceSheet = false
                                showTextPieceSheet = true
                            }
                        }
                    }
                )
            }


        }

        else -> viewModel.setNote(note)
    }


}