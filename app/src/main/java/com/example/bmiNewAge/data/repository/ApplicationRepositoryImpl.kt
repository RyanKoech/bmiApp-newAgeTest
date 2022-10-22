package com.example.bmiNewAge.data.repository

import com.example.bmiNewAge.common.BmiCategories
import com.example.bmiNewAge.common.Constants
import com.example.bmiNewAge.domain.repository.ApplicationRepository

class ApplicationRepositoryImpl : ApplicationRepository{
    override fun getBmiIndexCalculation(weight: Double, height: Double) = (weight / height / height) * 10000

    override fun getPonderalIndexCalculation(weight: Double, height: Double)  = (weight / height / height / height) * 1000000

    override fun getBmiCategory(bmi: Double): BmiCategories {
        return if(bmi < Constants.BMI_UPPER_UNDERWEIGHT) BmiCategories.UNDER_WEIGHT
        else if(bmi >= Constants.BMI_UPPER_UNDERWEIGHT && bmi < Constants.BMI_UPPER_HEALTHYWEIGHT) BmiCategories.HEALTHY_WEIGHT
        else if(bmi >= Constants.BMI_UPPER_HEALTHYWEIGHT && bmi < Constants.BMI_UPPER_OVERWEIGHT) BmiCategories.OVER_WEIGHT
        else BmiCategories.OBESITY
    }

    override fun getBmiMessage(name: String, bmiCategories: BmiCategories): String {
        return when(bmiCategories) {
            BmiCategories.UNDER_WEIGHT -> "Hello ${name}, you are underweight"
            BmiCategories.HEALTHY_WEIGHT -> "Hello ${name}, you are normal"
            BmiCategories.OVER_WEIGHT -> "Hello ${name}, you are overweight"
            else -> "Hello ${name}, you are obese"
        }
    }

    override fun getBmiMessageRange(bmiCategories: BmiCategories): String {
        return when(bmiCategories) {
            BmiCategories.UNDER_WEIGHT -> "Underweight bmi range: Below ${Constants.BMI_UPPER_UNDERWEIGHT}kg/m2"
            BmiCategories.HEALTHY_WEIGHT -> "Normal bmi range: ${Constants.BMI_UPPER_UNDERWEIGHT}kg.m2 - ${Constants.BMI_UPPER_HEALTHYWEIGHT}kg/m2"
            BmiCategories.OVER_WEIGHT -> "Overweight bmi range: ${Constants.BMI_UPPER_HEALTHYWEIGHT}kg.m2 - ${Constants.BMI_UPPER_OVERWEIGHT}kg/m2"
            else -> "Underweight bmi range: Above ${Constants.BMI_UPPER_OVERWEIGHT}kg/m2"
        }
    }
}