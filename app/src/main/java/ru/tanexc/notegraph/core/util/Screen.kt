package ru.tanexc.notegraph.core.util

sealed class Screen {

    data object NoteList : Screen()

    data object Settings : Screen()

    data object Login : Screen()

    data object Note : Screen()
}