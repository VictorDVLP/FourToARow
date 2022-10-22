package com.example.fourtoarow

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fourtoarow.DrawableTest.DrawableMatcher.withDrawable

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before

@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    @Before
    fun setup () {
        launchActivity<MainActivity>()
    }

    @Test
    fun insertPiece() {
        onView(withId(R.id.arrow1)).perform(click())
        onView(withId(R.id.box05)).check(matches(withDrawable(R.drawable.red_piece)))
    }

    @Test
    fun game_win_red() {
        onView(withId(R.id.arrow3)).perform(click())
        onView(withId(R.id.box17)).check(matches(withDrawable(R.drawable.red_piece)))
        onView(withId(R.id.arrow2)).perform(click())
        onView(withId(R.id.box11)).check(matches(withDrawable(R.drawable.yellow_piece)))
        onView(withId(R.id.arrow3)).perform(click())
        onView(withId(R.id.box16)).check(matches(withDrawable(R.drawable.red_piece)))
        onView(withId(R.id.arrow2)).perform(click())
        onView(withId(R.id.box10)).check(matches(withDrawable(R.drawable.yellow_piece)))
        onView(withId(R.id.arrow3)).perform(click())
        onView(withId(R.id.box15)).check(matches(withDrawable(R.drawable.red_piece)))
        onView(withId(R.id.arrow2)).perform(click())
        onView(withId(R.id.box09)).check(matches(withDrawable(R.drawable.yellow_piece)))
        onView(withId(R.id.arrow3)).perform(click())
        onView(withText(R.string.champion)).check(matches(isDisplayed()))
    }
}