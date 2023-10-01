package ru.tanexc.notegraph.core.util

sealed class Screen {

    data object Notes : Screen()

    data object Settings : Screen()

    data object Login : Screen()

    data object Note : Screen()
}