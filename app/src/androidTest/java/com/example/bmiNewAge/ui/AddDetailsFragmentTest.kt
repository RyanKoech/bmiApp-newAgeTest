package com.example.bmiNewAge.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.bmiNewAge.R
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class AddDetailsFragmentTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var scenario : FragmentScenario<AddDetailsFragment>
    private lateinit var navController: NavController

    @Before
    fun setup() {
        navController = mock(NavController::class.java)
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_BmiApp
        )

        scenario.onFragment{
            Navigation.setViewNavController(it.view!!, navController)
        }


    }

    @Test
    fun pressCalculateButtonWhenNameEmpty_errorSetOnNameTextView() {
        onView(withId(R.id.button_calculate)).perform(click())

        scenario.onFragment{
            assertThat(it.binding.editTextName.error.toString()).isNotEmpty()
        }
    }

    @Test
    fun pressCalculateButtonWhenNameEntered_errorNotSetOnNameTextView() {
        onView(withId(R.id.editText_name)).perform(replaceText("Name"))
        onView(withId(R.id.button_calculate)).perform(click())

        scenario.onFragment{
            assertThat(it.binding.editTextName.error).isNull()
        }
    }

    @Test
    fun pressCalculateButtonWhenNameEntered_navigateToResultsFragment() {
        val weight = 60
        val height = 160
        val name = "Name"

        scenario.onFragment{
            it.binding.numberPickerHeight.value = height
            it.binding.numberPickerWeight.value = weight
        }

        onView(withId(R.id.editText_name)).perform(replaceText(name))
        onView(withId(R.id.button_calculate)).perform(click())

        verify(navController).navigate(AddDetailsFragmentDirections.actionAddDetailsFragmentToResultsFragment(weight.toFloat(), height.toFloat(), name))
    }
}