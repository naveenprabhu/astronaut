package com.sapient.astronaut.astronautlist

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.R.id.action_bar
import androidx.appcompat.R.id.action_bar_container
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.sapient.astronaut.R
import com.sapient.astronaut.utils.Utils
import kotlinx.android.synthetic.main.astronaut.view.*
import java.util.concurrent.TimeUnit


@LargeTest
@RunWith(AndroidJUnit4::class)
class AstronautListActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(AstronautListActivity::class.java)

    @Test
    fun astronautsListActivityTest() {
        val expectedActionaBarName = "Astronauts"
        val textView = onView(
            allOf(
                withText(expectedActionaBarName),
                childAtPosition(
                    allOf(
                        withId(action_bar),
                        childAtPosition(
                            withId(action_bar_container),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText(expectedActionaBarName)))

        onView(isRoot()).perform(Utils().waitId(R.id.nameTextView, TimeUnit.SECONDS.toMillis(15)));



        val expectedName = "Franz Viehböck"
        val textView2 = onView(
            allOf(
                withId(R.id.nameTextView), withText(expectedName),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText(expectedName)))

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

}
