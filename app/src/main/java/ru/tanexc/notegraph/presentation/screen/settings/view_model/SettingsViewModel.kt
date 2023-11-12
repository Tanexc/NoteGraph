package ru.tanexc.notegraph.presentation.screen.settings.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t8rin.dynamic.theme.ColorTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tanexc.notegraph.domain.use_cases.settings.UpdateHeadlineOverflowBehaviourUseCase
import ru.tanexc.notegraph.domain.use_cases.settings.UpdateSettingsAmoledModeUseCase
import ru.tanexc.notegraph.domain.use_cases.settings.UpdateSettingsBordersEnabledUseCase
import ru.tanexc.notegraph.domain.use_cases.settings.UpdateSettingsColorTupleUseCase
import ru.tanexc.notegraph.domain.use_cases.settings.UpdateSettingsIsDarkModeUseCase
import ru.tanexc.notegraph.domain.use_cases.settings.UpdateUseDynamicColorsUseCase
import ru.tanexc.notegraph.presentation.util.HeadlineOverflowBehaviour
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val updateSettingsAmoledModeUseCase: UpdateSettingsAmoledModeUseCase,
    private val updateUseDynamicColorsUseCase: UpdateUseDynamicColorsUseCase,
    private val updateSettingsIsDarkModeUseCase: UpdateSettingsIsDarkModeUseCase,
    private val updateSettingsBordersEnabledUseCase: UpdateSettingsBordersEnabledUseCase,
    private val updateSettingsColorTupleUseCase: UpdateSettingsColorTupleUseCase,
    private val updateHeadlineOverflowBehaviourUseCase: UpdateHeadlineOverflowBehaviourUseCase
): ViewModel() {

    fun updateAmoledMode(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSettingsAmoledModeUseCase(enabled)
        }
    }

    fun updateUseDarkMode(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSettingsIsDarkModeUseCase(enabled)
        }
    }
    fun updateUseBorders(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSettingsBordersEnabledUseCase(enabled)
        }
    }

    fun updateUseDynamicColors(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateUseDynamicColorsUseCase(enabled)
        }
    }

    fun updateColorTuple(colors: ColorTuple) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSettingsColorTupleUseCase(colors)
        }
    }

    fun updateOverflowBehaviour(value: HeadlineOverflowBehaviour) {
        viewModelScope.launch(Dispatchers.IO) {
            updateHeadlineOverflowBehaviourUseCase(value)
        }
    }

}