package ru.tanexc.notegraph.domain.use_cases.note

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.interfaces.repository.NoteRepository
import ru.tanexc.notegraph.domain.model.Note
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(documentId: String): Flow<Action<Note?>> = repository.getById(documentId)
}