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
) : ViewModel() {
    private val _noteList: MutableState<List<Note>?> = mutableStateOf(null)
    val noteList: List<Note>? by _noteList

    private val _loading: MutableState<Boolean> = mutableStateOf(true)
    val loading: Boolean by _loading

    init {
            getByUserUseCase().onEach {
                when (it) {
                    is Action.Success -> {
                        _noteList.value = it.data
                        _loading.value = false
                    }

                    is Action.Error -> {
                        _loading.value = false
                        _noteList.value = null
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

}

