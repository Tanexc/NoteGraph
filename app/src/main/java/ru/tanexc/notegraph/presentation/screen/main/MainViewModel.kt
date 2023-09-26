package ru.tanexc.notegraph.presentation.screen.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import ru.tanexc.notegraph.core.util.Screen
import ru.tanexc.notegraph.domain.interfaces.repository.AuthRepository
import ru.tanexc.notegraph.domain.interfaces.use_cases.settings.GetSettingsAsFlowUseCase
import ru.tanexc.notegraph.domain.interfaces.use_cases.settings.GetSettingsUseCase
import ru.tanexc.notegraph.domain.model.Settings
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val getSettingsAsFlowUseCase: GetSettingsAsFlowUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
): ViewModel() {
    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.Login)
    val currentScreen: Screen by _currentScreen

    private val _settings: MutableState<Settings> = mutableStateOf(Settings.Default())
    val settings: Settings by _settings

    init {

        runBlocking {
            _settings.value = getSettingsUseCase()
        }

        getSettingsAsFlowUseCase().onEach {
            _settings.value = it
        }.launchIn(viewModelScope)

    }
}