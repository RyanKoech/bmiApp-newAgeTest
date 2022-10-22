package com.example.bmiNewAge.common

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