package ru.tanexc.notegraph.domain.interfaces.use_cases.settings

import ru.tanexc.notegraph.domain.interfaces.repository.SettingsRepository
import javax.inject.Inject

class UpdateSettingsIsDarkModeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(value: Boolean) = repository.updateIsDarkMode(value)
}