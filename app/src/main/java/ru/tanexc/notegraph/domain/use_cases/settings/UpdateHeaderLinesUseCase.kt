package ru.tanexc.notegraph.domain.use_cases.settings

import ru.tanexc.notegraph.domain.interfaces.repository.SettingsRepository
import javax.inject.Inject

class UpdateHeaderLinesUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(value: Int) = repository.updateHeaderLines(value)
}