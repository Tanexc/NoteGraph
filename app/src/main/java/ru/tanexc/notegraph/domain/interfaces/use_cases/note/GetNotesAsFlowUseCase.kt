package ru.tanexc.notegraph.domain.interfaces.use_cases.note

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.domain.interfaces.repository.NoteRepository
import ru.tanexc.notegraph.domain.model.Note
import javax.inject.Inject

class GetNotesAsFlowUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> = repository.notesFlow
}