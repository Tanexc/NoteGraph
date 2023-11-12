package ru.tanexc.notegraph.domain.use_cases.settings

import ru.tanexc.notegraph.domain.interfaces.repository.SettingsRepository
import ru.tanexc.notegraph.presentation.util.HeadlineOverflowBehaviour
import javax.inject.Inject

class UpdateHeadlineOverflowBehaviourUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(value: HeadlineOverflowBehaviour) = repository.updateHeadlineOverflowBehaviour(value)
}