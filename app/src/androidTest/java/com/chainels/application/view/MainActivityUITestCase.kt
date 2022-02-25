package com.chainels.application.view

import androidx.test.ext.junit.rules.ActivityScenarioRule
import junit.framework.TestCase
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chainels.application.R


@RunWith(AndroidJUnit4::class) // Annotate with @RunWith
class MainActivityUITestCase : TestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isListVisible_onAppLaunch() {
        onView(withId(R.id.recycler_view_timeline)).check(matches(isDisplayed()))
    }

}