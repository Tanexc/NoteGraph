package ru.tanexc.notegraph.domain.model.note

import ru.tanexc.notegraph.data.firebase.entity.NoteEntity
import ru.tanexc.notegraph.domain.interfaces.data_presenter.Domain

data class Note(
    val documentId: String = "",
    val label: String = "",
    val imagePieces: List<ImagePiece> = emptyList(),
    val textPieces: List<TextPiece> = emptyList(),
    val dependsOn: String = "",
    val endTo: Long = 0L
): Domain {

    companion object {
        fun Empty() = Note(
            documentId = "",
            label = "",
            imagePieces = emptyList(),
            textPieces = emptyList(),
            dependsOn = "",
            endTo = 0
        )
    }

    override fun asFirebaseEntity(): NoteEntity = NoteEntity(
        documentId = documentId,
        label = label,
        dependsOn = dependsOn,
        endTo = endTo
    )


}
