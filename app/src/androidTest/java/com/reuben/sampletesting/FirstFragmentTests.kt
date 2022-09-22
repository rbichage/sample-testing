package com.reuben.sampletesting

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirstFragmentTests {


    val scenario = launchFragmentInContainer<FirstFragment>(
        themeResId = R.style.Theme_SampleTesting
    )


    @Test
    fun test_isVisible() {
        onView(withId(R.id.button_first)).check(matches(isDisplayed()))
    }
}