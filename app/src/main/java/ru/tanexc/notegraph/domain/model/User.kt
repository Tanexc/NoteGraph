package ru.tanexc.notegraph.domain.model

data class User(
    val uid: String = "",
    val anonymous: Boolean = false,
    val email: String = "",
    val name: String = ""
): Domain {

    override fun asMap(): Map<String, Any> = mapOf(
        "uid" to uid,
        "anonymous" to anonymous,
        "email" to email,
        "name" to name
    )
}
