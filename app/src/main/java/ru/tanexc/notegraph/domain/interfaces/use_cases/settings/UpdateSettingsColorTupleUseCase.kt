package ru.tanexc.notegraph.domain.interfaces.use_cases.settings

import com.t8rin.dynamic.theme.ColorTuple
import ru.tanexc.notegraph.domain.interfaces.repository.SettingsRepository
import javax.inject.Inject

class UpdateSettingsColorTupleUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(value: ColorTuple) = repository.updateColorTuple(value)
}