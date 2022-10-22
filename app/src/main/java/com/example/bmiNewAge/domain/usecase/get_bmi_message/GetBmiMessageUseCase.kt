package com.example.bmiNewAge.domain.usecase.get_bmi_message

import com.example.bmiNewAge.common.BmiCategories
import com.example.bmiNewAge.domain.repository.ApplicationRepository
import javax.inject.Inject

class GetBmiMessageUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    operator fun invoke(name : String, bmiCategory : BmiCategories) : String {
        return repository.getBmiMessage(name, bmiCategory)
    }
}