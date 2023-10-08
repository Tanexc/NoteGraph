package ru.tanexc.notegraph.data.firebase.entity

import ru.tanexc.notegraph.domain.interfaces.data_presenter.FirebaseEntity
import ru.tanexc.notegraph.domain.model.note.Note

data class NoteEntity(
    val documentId: String = "",
    val label: String = "",
    val dependsOn: String = "",
    val endTo: Long = 0L
): FirebaseEntity {
    override fun asDomain(): Note = Note(
        documentId = documentId,
        label = label,
        imagePieces = emptyList(),
        textPieces = emptyList(),
        dependsOn = dependsOn,
        endTo = endTo
    )

    override fun asMap(): Map<String, Any> = mapOf(
        "documentId" to documentId,
        "label" to label,
        "dependsOn" to dependsOn,
        "endTo" to endTo
    )
}