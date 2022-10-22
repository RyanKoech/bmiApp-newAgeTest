package com.example.bmiNewAge.domain.usecase.get_bmi_message_range

import com.example.bmiNewAge.common.BmiCategories
import com.example.bmiNewAge.domain.repository.ApplicationRepository
import javax.inject.Inject

class GetBmiMessageRangeUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    operator fun invoke(bmiCategory : BmiCategories) : String {
        return repository.getBmiMessageRange(bmiCategory)
    }
}