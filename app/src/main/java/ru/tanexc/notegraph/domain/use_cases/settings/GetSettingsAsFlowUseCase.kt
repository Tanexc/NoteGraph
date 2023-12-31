package ru.tanexc.notegraph.domain.use_cases.settings

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.domain.interfaces.repository.SettingsRepository
import ru.tanexc.notegraph.domain.model.settings.Settings
import javax.inject.Inject

class GetSettingsAsFlowUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<Settings> = repository.getSettingsAsFlow()
}