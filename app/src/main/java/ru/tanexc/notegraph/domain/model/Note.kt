package ru.tanexc.notegraph.domain.model

data class Note(
    val documentId: String,
    val label: String,
    val imagePieces: List<ImagePiece>,
    val textPieces: List<TextPiece>,
    val dependsOn: String,
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

    override fun asMap(): Map<String, Any> = mapOf(
        "documentId" to documentId,
        "label" to label,
        "imagePieces" to imagePieces.map { it.documentId }.joinToString(" "),
        "textPieces" to textPieces.map { it.documentId }.joinToString(" "),
        "dependsOn" to dependsOn,
        "endTo" to endTo
    )
}
