package ru.tanexc.notegraph.domain.interfaces.use_cases.settings

import ru.tanexc.notegraph.domain.interfaces.repository.SettingsRepository
import javax.inject.Inject

class UpdateSettingsBordersEnabledUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(value: Boolean) = repository.updateBordersEnabled(value)
}