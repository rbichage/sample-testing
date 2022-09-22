package com.reuben.sampletesting

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.reuben.sampletesting.data.Person
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SecondFragmentTests {

    private lateinit var scenario: FragmentScenario<SecondFragment>


    @Before
    fun setup() {
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_SampleTesting
        )
    }

    @Test
    fun test_container_should_be_Visible_WhenUserOpensFragment() {
        onView(withId(R.id.second_root_container)).check(matches(isDisplayed()))
    }

    @Test
    fun test_recyclerView_should_be_Visible_WhenTheFragmentIsOpened() {
        test_container_should_be_Visible_WhenUserOpensFragment()
        onView(withId(R.id.recycler_people)).check(matches(isDisplayed()))
    }

    @Test
    fun test_recyclerView_scrolls_To_Bottom() {

        //Ideally, get this from the VH

        onView(withId(R.id.recycler_people))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(15, click()))

        onView(withText("wefobl w.kn. scs"))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    }

    @After
    fun tearDown() {
        scenario.close()
    }

}