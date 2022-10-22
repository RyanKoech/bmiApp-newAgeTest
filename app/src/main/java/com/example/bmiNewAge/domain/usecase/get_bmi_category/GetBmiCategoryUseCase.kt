package com.example.bmiNewAge.domain.usecase.get_bmi_category

import com.example.bmiNewAge.common.BmiCategories
import com.example.bmiNewAge.domain.repository.ApplicationRepository
import javax.inject.Inject

class GetBmiCategoryUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {

    operator fun invoke(bmi : Double) : BmiCategories {
        return repository.getBmiCategory(bmi)
    }
}