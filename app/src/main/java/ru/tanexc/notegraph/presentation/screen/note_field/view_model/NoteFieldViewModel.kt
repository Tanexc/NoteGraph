package ru.tanexc.notegraph.presentation.screen.note_field.view_model

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.model.note.ImagePiece
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.domain.model.note.TextPiece
import ru.tanexc.notegraph.domain.use_cases.piece.DeleteImagePieceUseCase
import ru.tanexc.notegraph.domain.use_cases.piece.DeleteTextPieceUseCase
import ru.tanexc.notegraph.domain.use_cases.piece.UpdateImagePieceUseCase
import ru.tanexc.notegraph.domain.use_cases.piece.UpdateTextPieceUseCase
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NoteFieldViewModel @Inject constructor(
    private val updateTextPieceUseCase: UpdateTextPieceUseCase,
    private val updateImagePieceUseCase: UpdateImagePieceUseCase,
    private val deleteTextPieceUseCase: DeleteTextPieceUseCase,
    private val deleteImagePieceUseCase: DeleteImagePieceUseCase
) : ViewModel() {
    private val _synchronizing: MutableState<Action<Unit>?> = mutableStateOf(Action.NotRunning(Unit))
    val synchronizing: Action<Unit>? by _synchronizing

    private val _focusedPieceId: MutableState<String?> = mutableStateOf(null)
    val focusedPieceId: String? by _focusedPieceId

    private val _note: MutableState<Note?> = mutableStateOf(null)
    val note: Note? by _note

    fun initializeNote(value: Note) {
        _note.value = value
    }

    fun deleteTextPiece(id: String) {
        note?.let { note ->
            viewModelScope.launch(Dispatchers.IO) {
                deleteTextPieceUseCase(
                    note.documentId,
                    note.textPieces.first { it.documentId == id }).collect {
                    _synchronizing.value = it
                }
            }
        }
    }

    fun deleteImagePiece(id: String) {
        note?.let { note ->
            viewModelScope.launch(Dispatchers.IO) {
                deleteImagePieceUseCase(
                    note.documentId,
                    note.imagePieces.first { it.documentId == id }).collect {
                    _synchronizing.value = it
                }
            }
        }
    }

    fun saveTextPiece(textPiece: TextPiece) {
        note?.let { note ->
            viewModelScope.launch(Dispatchers.IO) {
                updateTextPieceUseCase(
                    note.documentId,
                    textPiece
                ).collect {
                    _synchronizing.value = it
                }
            }
        }
    }

    fun saveImagePiece(imagePiece: ImagePiece) {
        note?.let { note ->
            viewModelScope.launch(Dispatchers.IO) {
                updateImagePieceUseCase(
                    note.documentId,
                    imagePiece
                ).collect {
                    _synchronizing.value = it
                }
            }
        }
    }

    fun createImagePiece(imageRequest: ImageRequest, context: Context) {
        val pieceId = UUID.randomUUID().toString()
        val loader = ImageLoader(context).newBuilder().build()
        viewModelScope.launch(Dispatchers.IO) {
            val imageBitmap =
                loader.execute(imageRequest).drawable?.toBitmapOrNull()?.asImageBitmap()
            saveImagePiece(
                ImagePiece
                    .empty()
                    .copy(documentId = pieceId, imageBitmap = imageBitmap)
            )
            _focusedPieceId.value = pieceId
        }
    }

    fun createTextPiece() {
        val pieceId = UUID.randomUUID().toString()
        viewModelScope.launch(Dispatchers.IO) {
            saveTextPiece(
                TextPiece
                    .empty()
                    .copy(documentId = pieceId)
            )
            _focusedPieceId.value = pieceId
        }
    }

    infix fun focusOnPiece(value: String?) {
        _focusedPieceId.value = value
    }
}