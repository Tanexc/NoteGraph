package ru.tanexc.notegraph.presentation.screen.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tanexc.notegraph.core.util.Screen
import ru.tanexc.notegraph.domain.interfaces.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.Login)
    val currentScreen: Screen by _currentScreen



    init {

    }
}