package ru.tanexc.notegraph.domain.model.user

import ru.tanexc.notegraph.data.firebase.entity.UserEntity
import ru.tanexc.notegraph.domain.interfaces.data_presenter.Domain

data class User(
    val uid: String = "",
    val anonymous: Boolean = false,
    val email: String = "",
    val name: String = ""
) : Domain {
    override fun asFirebaseEntity(): UserEntity = UserEntity(
        uid,
        anonymous,
        email,
        name
    )
}
