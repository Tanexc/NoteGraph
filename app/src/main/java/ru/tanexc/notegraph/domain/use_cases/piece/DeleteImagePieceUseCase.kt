package ru.tanexc.notegraph.domain.use_cases.piece

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.interfaces.repository.NoteRepository
import ru.tanexc.notegraph.domain.model.note.ImagePiece
import javax.inject.Inject

class DeleteImagePieceUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(noteId: String, imagePiece: ImagePiece): Flow<Action<Unit>> = repository.deleteImagePiece(noteId, imagePiece)
}