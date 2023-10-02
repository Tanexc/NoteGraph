package ru.tanexc.notegraph.data.firebase.entity

import ru.tanexc.notegraph.domain.interfaces.data_presenter.FirebaseEntity
import ru.tanexc.notegraph.domain.model.user.User

data class UserEntity(
    val uid: String = "",
    val anonymous: Boolean = false,
    val email: String = "",
    val name: String = ""
) : FirebaseEntity {
    override fun asDomain(): User = User(
        uid = uid,
        anonymous = anonymous,
        email = email,
        name = name
    )

    override fun asMap(): Map<String, Any> = mapOf(
        "uid" to uid,
        "anonymous" to anonymous,
        "email" to email,
        "name" to name
    )
}