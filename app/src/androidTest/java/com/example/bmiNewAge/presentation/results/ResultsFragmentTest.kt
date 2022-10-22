package com.example.bmiNewAge.presentation.results

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.bmiNewAge.R
import com.example.bmiNewAge.common.Constants
import com.example.bmiNewAge.common.Utilities
import com.google.common.truth.Truth.assertThat
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class ResultsFragmentTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var scenario : FragmentScenario<ResultsFragment>
    private lateinit var navController: NavController
    private var weight : Float = 60F
    private var height : Float = 160F
    private var name : String = "Name"

    @Before
    fun setup() {
        navController = Mockito.mock(NavController::class.java)
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_BmiApp,
            fragmentArgs = bundleOf("weight" to weight, "height" to height, "name" to name)
        )

        scenario.onFragment{
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

    @Test
    fun passedNameFragmentArgument_isDisplayed() {

        val userMessageTextView = onView(allOf(
            withId(R.id.textView_userMessage),
            withText(name),
            isDisplayed()
        ))

        assertThat(userMessageTextView).isNotNull()
    }

    @Test
    fun passedFragmentArguments_correctPonderalIndexDisplayed() {

        val ponderalIndex = Utilities.calculatePonderalIndex(weight.toDouble(), height.toDouble())
        val ponderalIndexTwoDp = Utilities.roundOffToTwoDp(ponderalIndex)

        val ponderalTextView = onView(allOf(
            withId(R.id.textView_ponderalResult),
            withText(ponderalIndexTwoDp),
            isDisplayed()
        ))

        assertThat(ponderalTextView).isNotNull()
    }

    @Test
    fun passedFragmentArguments_correctBmiWholeNumberDisplayed() {

        val bmi = Utilities.calculateBmi(weight.toDouble(), height.toDouble())
        val bmiWholeNumber = Utilities.getFormatBmiResult(bmi)[0]

        val bmiWholeTextView = onView(allOf(
            withId(R.id.textView_bmiWhole),
            withText(bmiWholeNumber),
            isDisplayed()
        ))

        assertThat(bmiWholeTextView).isNotNull()
    }

    @Test
    fun passedFragmentArguments_correctBmiDecimalDisplayed() {

        val bmi = Utilities.calculateBmi(weight.toDouble(), height.toDouble())
        val bmiDecimal = Utilities.getFormatBmiResult(bmi)[1]

        val bmiDecimalTextView = onView(allOf(
            withId(R.id.textView_bmiDecimal),
            withText(bmiDecimal),
            isDisplayed()
        ))

        assertThat(bmiDecimalTextView).isNotNull()
    }

    @Test
    fun passedUnderweightArgument_correctMessagesDisplayed() {
        val underWeight = 40F
        val scenario :FragmentScenario<ResultsFragment> = launchFragmentInContainer(
            themeResId = R.style.Theme_BmiApp,
            fragmentArgs = bundleOf("weight" to underWeight, "height" to height, "name" to name)
        )

        scenario.onFragment{
            Navigation.setViewNavController(it.view!!, navController)
        }

        val userMessageTextView = onView(allOf(
            withId(R.id.textView_userMessage),
            withText("underweight")
        ))

        val bmiRangeTextView = onView(allOf(
            withId(R.id.textView_bmiRange),
            withText("${Constants.BMI_UPPER_UNDERWEIGHT}kg/m2")
        ))

        assertThat(userMessageTextView).isNotNull()
        assertThat(bmiRangeTextView).isNotNull()
    }

    @Test
    fun passedNormalWeightArgument_correctMessagesDisplayed() {
        val normalWeight = 50F
        val scenario :FragmentScenario<ResultsFragment> = launchFragmentInContainer(
            themeResId = R.style.Theme_BmiApp,
            fragmentArgs = bundleOf("weight" to normalWeight, "height" to height, "name" to name)
        )

        scenario.onFragment{
            Navigation.setViewNavController(it.view!!, navController)
        }

        val userMessageTextView = onView(allOf(
            withId(R.id.textView_userMessage),
            withText("normal")
        ))

        val bmiRangeTextView = onView(allOf(
            withId(R.id.textView_bmiRange),
            withText("${Constants.BMI_UPPER_UNDERWEIGHT}kg.m2 - ${Constants.BMI_UPPER_HEALTHYWEIGHT}kg/m2")
        ))

        assertThat(userMessageTextView).isNotNull()
        assertThat(bmiRangeTextView).isNotNull()
    }

    @Test
    fun passedOverweightArgument_correctMessagesDisplayed() {
        val overWeight = 70F
        val scenario :FragmentScenario<ResultsFragment> = launchFragmentInContainer(
            themeResId = R.style.Theme_BmiApp,
            fragmentArgs = bundleOf("weight" to overWeight, "height" to height, "name" to name)
        )

        scenario.onFragment{
            Navigation.setViewNavController(it.view!!, navController)
        }

        val userMessageTextView = onView(allOf(
            withId(R.id.textView_userMessage),
            withText("overweight")
        ))

        val bmiRangeTextView = onView(allOf(
            withId(R.id.textView_bmiRange),
            withText("${Constants.BMI_UPPER_HEALTHYWEIGHT}kg.m2 - ${Constants.BMI_UPPER_OVERWEIGHT}kg/m2")
        ))

        assertThat(userMessageTextView).isNotNull()
        assertThat(bmiRangeTextView).isNotNull()
    }

    @Test
    fun passedObeseWeightArgument_correctMessagesDisplayed() {
        val obeseWeight = 85F
        val scenario :FragmentScenario<ResultsFragment> = launchFragmentInContainer(
            themeResId = R.style.Theme_BmiApp,
            fragmentArgs = bundleOf("weight" to obeseWeight, "height" to height, "name" to name)
        )

        scenario.onFragment{
            Navigation.setViewNavController(it.view!!, navController)
        }

        val userMessageTextView = onView(allOf(
            withId(R.id.textView_userMessage),
            withText("obese")
        ))

        val bmiRangeTextView = onView(allOf(
            withId(R.id.textView_bmiRange),
            withText("${Constants.BMI_UPPER_OVERWEIGHT}kg/m2")
        ))

        assertThat(userMessageTextView).isNotNull()
        assertThat(bmiRangeTextView).isNotNull()
    }
}