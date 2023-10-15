package ru.tanexc.notegraph.domain.use_cases.note

import ru.tanexc.notegraph.domain.interfaces.repository.NoteRepository
import javax.inject.Inject

class GetNoteAsFlowUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(noteId: String) = repository.getNoteAsFlow(noteId)
}