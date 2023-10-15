package ru.tanexc.notegraph.core.util

sealed class Action<T>(val data: T) {
    class NotRunning<T>(data: T): Action<T>(data)
    class Loading<T>(data: T): Action<T>(data)
    class Success<T>(data: T): Action<T>(data)
    class Error<T>(data: T, val messsage: String?): Action<T>(data)
}
