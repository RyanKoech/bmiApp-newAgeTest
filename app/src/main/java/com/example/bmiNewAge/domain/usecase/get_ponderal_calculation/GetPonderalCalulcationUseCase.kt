package com.example.bmiNewAge.domain.usecase.get_ponderal_calculation

import com.example.bmiNewAge.domain.repository.ApplicationRepository
import javax.inject.Inject

class GetPonderalCalulcationUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    operator fun invoke(weight: Double, height: Double) : Double {
        return repository.getPonderalIndexCalculation(weight, height)
    }
}