package ru.tanexc.notegraph.presentation.screen.notes.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.model.Note
import ru.tanexc.notegraph.domain.use_cases.note.DeleteNoteUseCase
import ru.tanexc.notegraph.domain.use_cases.note.GetByUserUseCase
import ru.tanexc.notegraph.domain.use_cases.note.GetNoteByIdUseCase
import ru.tanexc.notegraph.domain.use_cases.note.GetNotesAsFlowUseCase
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val getNotesAsFlowUseCase: GetNotesAsFlowUseCase,
    private val getByUserUseCase: GetByUserUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
): ViewModel() {
    private val _noteList: MutableState<List<Note>?> = mutableStateOf(emptyList())
    val noteList: List<Note>? by _noteList

    private val _loading: MutableState<Boolean> = mutableStateOf(true)
    val loading: Boolean by _loading

    init {

        viewModelScope.launch(Dispatchers.IO) {
            getByUserUseCase().collect {
                when (it) {
                    is Action.Loading -> _loading.value = true
                    is Action.Success -> {
                        _loading.value = false
                        _noteList.value = it.data
                    }
                    is Action.Error -> {
                        _loading.value = false
                        _noteList.value = null
                    }
                }
            }

            getNotesAsFlowUseCase().collect {
                _noteList.value = it
            }
        }

    }



}