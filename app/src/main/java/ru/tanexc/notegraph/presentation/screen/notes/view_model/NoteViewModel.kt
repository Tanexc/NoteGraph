package ru.tanexc.notegraph.presentation.screen.notes.view_model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.model.note.ImagePiece
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.domain.model.note.TextPiece
import ru.tanexc.notegraph.domain.use_cases.note.SaveNoteUseCase
import ru.tanexc.notegraph.domain.use_cases.note.UpdateImagePieceUseCase
import ru.tanexc.notegraph.domain.use_cases.note.UpdateNoteUseCase
import ru.tanexc.notegraph.domain.use_cases.note.UpdateTextPieceUseCase
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val saveNoteUseCase: SaveNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val updateTextPieceUseCase: UpdateTextPieceUseCase,
    private val updateImagePieceUseCase: UpdateImagePieceUseCase
) : ViewModel() {
    private val _focusedPieceId: MutableState<String?> = mutableStateOf(null)
    val focusedPieceId: String? by _focusedPieceId

    private val _synchronizing: MutableState<Action<Unit>?> = mutableStateOf(null)
    val synchronizing: Action<Unit>? by _synchronizing

    private val _note: MutableState<Note?> = mutableStateOf(null)
    val note: Note? by _note


    fun setNote(note: Note) {
        if (note.documentId == "" && _synchronizing.value == null) {
            saveNoteUseCase(note).onEach {
                when (it) {
                    is Action.Success -> {
                        _synchronizing.value = Action.Success(Unit)
                        it.data?.let { note ->
                            _note.value = note
                        }
                        _note.value = note
                    }

                    is Action.Error -> {
                        _synchronizing.value = Action.Error(Unit, messsage = null)
                    }

                    is Action.Loading -> {
                        _synchronizing.value = Action.Loading(Unit)
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            _note.value = note
        }
    }

    fun createImagePiece(imageRequest: ImageRequest, context: Context) {
        note?.let { note ->
            val loader = ImageLoader(context).newBuilder().build()
            viewModelScope.launch(Dispatchers.IO) {
                val imageBitmap =
                    loader.execute(imageRequest).drawable?.toBitmapOrNull()?.asImageBitmap()

                _focusedPieceId.value = "ImagePiece#${UUID.randomUUID()}"
                updateImagePieces(
                    note.imagePieces + ImagePiece.empty()
                        .copy(documentId = focusedPieceId!!, imageBitmap = imageBitmap)
                )
            }


        }
    }

    fun createTextPiece() {
        note?.let { note ->
            _focusedPieceId.value = "TextPiece#${UUID.randomUUID()}"
            updateTextPieces(
                note.textPieces + TextPiece.empty().copy(documentId = focusedPieceId!!)
            )
        }
    }

    fun updateFocusedPiece(value: String?) {
        _focusedPieceId.value = value
    }

    private fun updateTextPieces(value: List<TextPiece>) {
        note?.let { note ->
            updateNote(note.copy(textPieces = value))
        }

    }

    private fun updateImagePieces(value: List<ImagePiece>) {
        note?.let { note ->
            updateNote(note.copy(imagePieces = value))
        }
    }

    fun updateImagePiece(value: ImagePiece) {
        note?.let { note ->
            _note.value = note.copy(
                imagePieces = note.imagePieces
                    .toMutableList()
                    .apply {
                        this[this.indexOf(this.first { it.documentId == value.documentId })] = value
                    }
            )
        }

    }

    fun updateTextPiece(value: TextPiece) {
        note?.let { note ->
            _note.value = note.copy(
                textPieces = note.textPieces
                    .toMutableList()
                    .apply {
                        this[this.indexOf(this.first { it.documentId == value.documentId })] = value
                    }
            )
        }
    }

    fun updateNote(note: Note) {
        _note.value = note
        viewModelScope.launch(Dispatchers.IO) {
            updateNoteUseCase(note).collect {
                _synchronizing.value = it
                Log.i("update", "note ${(it as? Action.Error)?.messsage}")
            }
        }
    }

    fun updateNote() {
        note?.let { note ->
            viewModelScope.launch(Dispatchers.IO) {
                updateNoteUseCase(note).collect {
                    _synchronizing.value = it
                }
            }
        }
    }

    fun saveTextPiece(id: String) {
        note?.let { note ->
            viewModelScope.launch(Dispatchers.IO) {
                updateTextPieceUseCase(note.documentId, note.textPieces.first { it.documentId == id }).collect {
                    _synchronizing.value = it
                    Log.i("update", "text piece ${(it as? Action.Error)?.messsage}")
                }
            }
        }
    }

    fun saveImagePiece(id: String) {
        note?.let { note ->
            viewModelScope.launch(Dispatchers.IO) {
                updateImagePieceUseCase(note.documentId, note.imagePieces.first { it.documentId == id }).collect {
                    _synchronizing.value = it
                    Log.i("update", "image piece ${(it as? Action.Error)?.messsage}")
                }
            }
        }
    }
}