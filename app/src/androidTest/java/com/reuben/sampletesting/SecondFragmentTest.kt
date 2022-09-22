package com.reuben.sampletesting

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SecondFragmentTest {

    val scenario = launchFragmentInContainer<SecondFragment>(
        themeResId = R.style.Theme_SampleTesting
    )

    @Test
    fun test_container_should_be_Visible_WhenUserOpensFragment() {
        onView(withId(R.id.second_root_container))
            .check(matches(isDisplayed()))

    }

}