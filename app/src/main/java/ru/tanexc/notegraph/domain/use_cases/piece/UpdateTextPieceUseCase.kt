package ru.tanexc.notegraph.domain.use_cases.piece

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.interfaces.repository.NoteRepository
import ru.tanexc.notegraph.domain.model.note.TextPiece
import javax.inject.Inject

class UpdateTextPieceUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(noteId: String, textPiece: TextPiece): Flow<Action<Unit>> = repository.updateTextPiece(noteId, textPiece)
}