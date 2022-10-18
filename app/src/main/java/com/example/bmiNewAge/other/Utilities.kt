package com.example.bmiNewAge.other

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
}