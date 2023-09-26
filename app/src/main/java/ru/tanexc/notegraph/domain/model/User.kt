package ru.tanexc.notegraph.domain.model

data class User(
    val documentId: String? = null,
    val uid: String,
    val isAnonymous: Boolean = false,
    val email: String,
    val name: String
): Domain {
    override fun asMap(): Map<String, Any> = mapOf(
        "documentId" to (documentId?: ""),
        "uid" to uid,
        "isAnonymous" to isAnonymous,
        "email" to email,
        "name" to name
    )
}
