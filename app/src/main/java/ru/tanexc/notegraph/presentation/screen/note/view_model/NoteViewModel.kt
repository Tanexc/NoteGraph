package ru.tanexc.notegraph.presentation.screen.note.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.core.util.SheetContent
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.domain.use_cases.note.GetNoteAsFlowUseCase
import ru.tanexc.notegraph.domain.use_cases.note.SaveNoteUseCase
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val saveNoteUseCase: SaveNoteUseCase,
    private val getNoteAsFlowUseCase: GetNoteAsFlowUseCase
) : ViewModel() {
    private val _focusedPieceId: MutableState<String?> = mutableStateOf(null)
    val focusedPieceId: String? by _focusedPieceId

    private val _synchronizing: MutableState<Action<Unit>?> = mutableStateOf(Action.NotRunning(Unit))
    val synchronizing: Action<Unit>? by _synchronizing

    private val _note: MutableState<Note?> = mutableStateOf(null)
    val note: Note? by _note

    private val _sheetContent: MutableState<SheetContent> = mutableStateOf(SheetContent.None)
    val sheetContent: SheetContent by _sheetContent


    fun initializeNote(noteId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getNoteAsFlowUseCase(noteId).collect {
                when (it) {
                    is Action.Success -> {
                        _note.value = it.data
                        _synchronizing.value = Action.Success(Unit)
                    }
                    is Action.Error -> _synchronizing.value = Action.Error(Unit, it.messsage)
                    else -> _synchronizing.value = Action.Loading(Unit)
                }
            }
        }
    }

    fun saveNote(note: Note) {
        _note.value = note
        viewModelScope.launch(Dispatchers.IO) {
            saveNoteUseCase(note).collect {
                _synchronizing.value = it
            }
        }
    }

    infix fun openSheetContent(value: SheetContent) {
        _sheetContent.value = value
    }
}