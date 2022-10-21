package com.example.bmiNewAge.common

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilitiesTest {

    @Test
    fun `calculate BMI with height 160cm weight 51,2kg returns 20`() {

        val weight = 51.2
        val height = 160.0

        val bmi = Utilities.calculateBmi(weight, height)
        assertThat(bmi).isEqualTo(20.0)
    }

    @Test
    fun `calculate Ponderal with height 160cm weight 40,96kg returns 10`() {

        val weight = 40.96
        val height = 160.0

        val bmi = Utilities.calculatePonderalIndex(weight, height)
        assertThat(bmi).isEqualTo(10.0)
    }

}
