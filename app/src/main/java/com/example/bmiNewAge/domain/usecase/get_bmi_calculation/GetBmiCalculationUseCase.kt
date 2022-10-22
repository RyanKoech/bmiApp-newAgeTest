package com.example.bmiNewAge.domain.usecase.get_bmi_calculation

import com.example.bmiNewAge.domain.repository.ApplicationRepository
import javax.inject.Inject

class GetBmiCalculationUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {

    operator fun invoke(weight: Double, height: Double) : Double {
        return repository.getBmiIndexCalculation(weight, height)
    }
}