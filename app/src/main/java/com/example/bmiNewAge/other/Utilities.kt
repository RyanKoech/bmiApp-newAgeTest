package com.example.bmiNewAge.other

import java.math.RoundingMode
import java.text.DecimalFormat

enum class Genders{
    MALE, FEMALE
}

enum class BmiCategories{
    UNDER_WEIGHT, HEALTHY_WEIGHT, OVER_WEIGHT, OBESITY
}

object Utilities {

    val genders : ArrayList<Genders> = arrayListOf(
        Genders.MALE,
        Genders.FEMALE
    )

    fun getGenderString () : Array<String> {
        val genderStringList : MutableList<String> = arrayListOf()

        for (i in 0 until (genders.size-1))
        genders.forEach { gender ->
            genderStringList.add(i, gender.toString())
        }

        return genderStringList.toTypedArray()
    }

    fun calculateBmi(weight: Double, height : Double) = (weight / height / height) * 10000

    fun calculatePonderalIndex(weight : Double, height : Double) = (weight / height / height / height) * 1000000

    fun getBmiCategory(bmi : Double) : BmiCategories{
        return if(bmi < Constants.BMI_UPPER_UNDERWEIGHT) BmiCategories.UNDER_WEIGHT
        else if(bmi >= Constants.BMI_UPPER_UNDERWEIGHT && bmi < Constants.BMI_UPPER_HEALTHYWEIGHT) BmiCategories.HEALTHY_WEIGHT
        else if(bmi >= Constants.BMI_UPPER_HEALTHYWEIGHT && bmi < Constants.BMI_UPPER_OVERWEIGHT) BmiCategories.OVER_WEIGHT
        else BmiCategories.OBESITY
    }

    fun getUserBmiResultMessage(name: String, bmiCategories: BmiCategories) : String {
        return when(bmiCategories) {
            BmiCategories.UNDER_WEIGHT -> "Hello ${name}, you are underweight"
            BmiCategories.HEALTHY_WEIGHT -> "Hello ${name}, you are normal"
            BmiCategories.OVER_WEIGHT -> "Hello ${name}, you are overweight"
            else -> "Hello ${name}, you are obese"
        }
    }

    fun getBmiRangeMessage(bmiCategories: BmiCategories) : String {
        return when(bmiCategories) {
            BmiCategories.UNDER_WEIGHT -> "Underweight bmi range: Below ${Constants.BMI_UPPER_UNDERWEIGHT}kg/m2"
            BmiCategories.HEALTHY_WEIGHT -> "Normal bmi range: ${Constants.BMI_UPPER_UNDERWEIGHT}kg.m2 - ${Constants.BMI_UPPER_HEALTHYWEIGHT}kg/m2"
            BmiCategories.OVER_WEIGHT -> "Overweight bmi range: ${Constants.BMI_UPPER_HEALTHYWEIGHT}kg.m2 - ${Constants.BMI_UPPER_OVERWEIGHT}kg/m2"
            else -> "Underweight bmi range: Above ${Constants.BMI_UPPER_OVERWEIGHT}kg/m2"
        }
    }

    fun roundOffToTwoDp(number: Double): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        return df.format(number)
    }

    fun getFormatBmiResult(bmi : Double) : List<String> {
        val bmiTwoDp = roundOffToTwoDp(bmi)
        return bmiTwoDp.split(".")
    }
}