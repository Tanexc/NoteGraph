package ru.tanexc.notegraph.presentation.screen.notes.view_model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.domain.model.note.ImagePiece
import ru.tanexc.notegraph.domain.interfaces.data_presenter.NotePiece
import ru.tanexc.notegraph.domain.model.note.TextPiece
import ru.tanexc.notegraph.domain.use_cases.note.SaveNoteUseCase
import ru.tanexc.notegraph.domain.use_cases.note.UpdateNoteUseCase
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val saveNoteUseCase: SaveNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {
    private val _focusedPiece: MutableState<NotePiece?> = mutableStateOf(null)
    val focusedPiece: NotePiece? by _focusedPiece

    private val _synchronizing: MutableState<Action<Unit>?> = mutableStateOf(null)
    val synchroinizing: Action<Unit>? by _synchronizing

    private val _note: MutableState<Note> = mutableStateOf(Note.Empty())
    val note: Note by _note

    fun setNote(note: Note) {
        _note.value = note
        if (note.documentId == "" && synchroinizing == null) {
            saveNoteUseCase(note).onEach {
                when (it) {
                    is Action.Success -> {
                        _synchronizing.value = Action.Success(Unit)
                        it.data?.let { note ->
                            _note.value = note
                        }
                    }
                    is Action.Error -> {
                        Log.i("CUM", it.messsage?: "err")
                        _synchronizing.value = Action.Error(Unit, messsage = null)
                    }

                    is Action.Loading -> {
                        _synchronizing.value = Action.Loading(Unit)
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    fun updateFocusedPiece(value: NotePiece?) {
        _focusedPiece.value = value
    }

    fun updateTextPieces(value: List<TextPiece>) {
        _note.value = note.copy(textPieces = value)
        viewModelScope.launch(Dispatchers.IO) {
            updateNoteUseCase(note).collect {
                _synchronizing.value = it
            }
        }

    }

    fun updateImagePieces(value: List<ImagePiece>) {
        _note.value = note.copy(imagePieces = value)
        viewModelScope.launch(Dispatchers.IO) {
            updateNoteUseCase(note).collect {
                _synchronizing.value = it
            }
        }
    }

}