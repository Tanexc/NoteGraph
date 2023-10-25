package ru.tanexc.notegraph.presentation.screen.notes_list.view_model

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
import ru.tanexc.notegraph.domain.use_cases.note.CreateNoteUseCase
import ru.tanexc.notegraph.domain.use_cases.note.DeleteNoteUseCase
import ru.tanexc.notegraph.domain.use_cases.note.GetByUserUseCase
import ru.tanexc.notegraph.domain.use_cases.note.GetNotesAsFlowUseCase
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNotesAsFlowUseCase: GetNotesAsFlowUseCase,
    private val getByUserUseCase: GetByUserUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val createNoteUseCase: CreateNoteUseCase
) : ViewModel() {
    private val _noteList: MutableState<List<Note>> = mutableStateOf(emptyList())
    val noteList: List<Note> by _noteList

    private val _synchronizing: MutableState<Action<Unit>?> = mutableStateOf(Action.NotRunning(Unit))
    val synchronizing: Action<Unit>? by _synchronizing

    init {
        getByUserUseCase().onEach {
            when (it) {
                is Action.Success -> {
                    _noteList.value = it.data
                    _synchronizing.value = Action.Success(Unit)
                }

                is Action.Error -> {
                    _synchronizing.value = Action.Error(Unit, messsage = it.messsage)
                }

                is Action.Loading -> {
                    _synchronizing.value = Action.Loading(Unit)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)

        viewModelScope.launch(Dispatchers.IO) {
            getNotesAsFlowUseCase().collect {
                _noteList.value = it
            }
        }
    }

    fun createNote(onSuccess: (Note) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            createNoteUseCase().collect { action ->
                when(action) {
                    is Action.Success -> {
                        _synchronizing.value = Action.Success(Unit)
                        action.data?.let {onSuccess(it)}
                    }
                    is Action.Loading -> {
                        _synchronizing.value = Action.Loading(Unit)
                    }
                    is Action.Error -> {
                        _synchronizing.value = Action.Error(Unit, messsage = action.messsage)
                    }
                    else -> {}
                }
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase(note).collect { action ->
                when(action) {
                    is Action.Success -> {
                        _synchronizing.value = Action.Success(Unit)
                    }
                    is Action.Loading -> {
                        _synchronizing.value = Action.Loading(Unit)
                    }
                    is Action.Error -> {
                        _synchronizing.value = Action.Error(Unit, messsage = action.messsage)
                    }
                    else -> {}
                }
            }
        }
    }
}

