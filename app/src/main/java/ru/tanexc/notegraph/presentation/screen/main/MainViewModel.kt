package ru.tanexc.notegraph.presentation.screen.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.tanexc.notegraph.core.util.Screen
import ru.tanexc.notegraph.domain.use_cases.settings.GetSettingsAsFlowUseCase
import ru.tanexc.notegraph.domain.use_cases.settings.GetSettingsUseCase
import ru.tanexc.notegraph.domain.use_cases.user.GetUserAsFlowUseCase
import ru.tanexc.notegraph.domain.use_cases.user.SignOutUserUseCase
import ru.tanexc.notegraph.domain.model.settings.Settings
import ru.tanexc.notegraph.domain.model.user.User
import ru.tanexc.notegraph.domain.use_cases.user.TryGetLocalUserUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getUserAsFlowUseCase: GetUserAsFlowUseCase,
    getSettingsAsFlowUseCase: GetSettingsAsFlowUseCase,
    getSettingsUseCase: GetSettingsUseCase,
    tryGetLocalUserUseCase: TryGetLocalUserUseCase,
    private val signOutUserUseCase: SignOutUserUseCase
) : ViewModel() {
    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.Login)
    val currentScreen: Screen by _currentScreen

    private val _settings: MutableState<Settings> = mutableStateOf(Settings.Default())
    val settings: Settings by _settings

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: User? by _user

    init {
        runBlocking {
            _user.value = tryGetLocalUserUseCase()
            _currentScreen.value = when (user) {
                is User -> Screen.Notes
                else -> Screen.Login
            }
            _settings.value = getSettingsUseCase()
        }

        viewModelScope.launch(Dispatchers.IO) {
            getUserAsFlowUseCase().collect {
                _user.value = it
                _currentScreen.value = when (user) {
                    is User -> Screen.Notes
                    else -> Screen.Login
                }
            }
        }

        getSettingsAsFlowUseCase().onEach {
            _settings.value = it
        }.launchIn(viewModelScope)

    }

    fun updateCurrentScreen(screen: Screen) {
        _currentScreen.value = screen
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUserUseCase()
        }
    }

}