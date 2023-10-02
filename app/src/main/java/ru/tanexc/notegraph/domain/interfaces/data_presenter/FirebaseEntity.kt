package ru.tanexc.notegraph.domain.interfaces.data_presenter

interface FirebaseEntity {
    fun asDomain(): Domain

    fun asMap(): Map<String, Any>
}