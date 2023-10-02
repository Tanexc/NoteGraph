package ru.tanexc.notegraph.domain.interfaces.data_presenter

interface Domain {
    fun asFirebaseEntity(): FirebaseEntity
}