package ru.tanexc.notegraph.presentation.screen.notes.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tanexc.notegraph.domain.model.Note
import ru.tanexc.notegraph.domain.model.ImagePiece
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(): ViewModel() {
    private val _focusedPiece: MutableState<ImagePiece?> = mutableStateOf(null)
    val focusedPiece: ImagePiece? by _focusedPiece

    private val _note: MutableState<Note?> = mutableStateOf(null)
    val note: Note? by _note

    fun updateFocusedPiece(value: ImagePiece?) {
        _focusedPiece.value = value
    }

    suspend fun updateNote(value: Note) {

    }

}