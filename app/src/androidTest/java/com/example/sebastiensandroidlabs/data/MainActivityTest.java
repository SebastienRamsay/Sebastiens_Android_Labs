package com.example.sebastiensandroidlabs.data;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.sebastiensandroidlabs.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.cityEditText)));


        appCompatEditText.perform(click());


        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.cityEditText)));
        appCompatEditText2.perform(replaceText("12345"), closeSoftKeyboard());


        ViewInteraction materialButton = onView(allOf(withId(R.id.getForecastButton)));
        materialButton.perform(click());


        ViewInteraction textView = onView(allOf(withId(R.id.instructionsTextView)));
        textView.check(matches(withText("YOU SHALL NOT PASS!")));

    }

    /**
     * @author Sebastien Ramsay
     *
     * Tests the login requirements to make sure an upper case letter is required.
     */
    @Test
    public void testFindMissingUpperCase() {

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.cityEditText)));


        appCompatEditText.perform(click());


        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.cityEditText)));
        appCompatEditText2.perform(replaceText("password123#$*"), closeSoftKeyboard());


        ViewInteraction materialButton = onView(allOf(withId(R.id.getForecastButton)));
        materialButton.perform(click());


        ViewInteraction textView = onView(allOf(withId(R.id.instructionsTextView)));
        textView.check(matches(withText("YOU SHALL NOT PASS!")));

    }

    /**
     * @author Sebastien Ramsay
     *
     * Tests the login requirements to make sure an lower case letter is required.
     */
    @Test
    public void testFindMissingLowerCase() {

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.cityEditText)));


        appCompatEditText.perform(click());


        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.cityEditText)));
        appCompatEditText2.perform(replaceText("PASSWORD123#$*"), closeSoftKeyboard());


        ViewInteraction materialButton = onView(allOf(withId(R.id.getForecastButton)));
        materialButton.perform(click());


        ViewInteraction textView = onView(allOf(withId(R.id.instructionsTextView)));
        textView.check(matches(withText("YOU SHALL NOT PASS!")));

    }

    /**
     * @author Sebastien Ramsay
     *
     * Tests the login requirements to make sure a special character is required.
     */
    @Test
    public void testFindMissingSpecialCharacter() {

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.cityEditText)));


        appCompatEditText.perform(click());


        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.cityEditText)));
        appCompatEditText2.perform(replaceText("Password123"), closeSoftKeyboard());


        ViewInteraction materialButton = onView(allOf(withId(R.id.getForecastButton)));
        materialButton.perform(click());


        ViewInteraction textView = onView(allOf(withId(R.id.instructionsTextView)));
        textView.check(matches(withText("YOU SHALL NOT PASS!")));

    }

    /**
     * @author Sebastien Ramsay
     *
     * Tests the login requirements to make sure a digit is required.
     */
    @Test
    public void testFindMissingDigit() {

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.cityEditText)));


        appCompatEditText.perform(click());


        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.cityEditText)));
        appCompatEditText2.perform(replaceText("Password#$*"), closeSoftKeyboard());


        ViewInteraction materialButton = onView(allOf(withId(R.id.getForecastButton)));
        materialButton.perform(click());


        ViewInteraction textView = onView(allOf(withId(R.id.instructionsTextView)));
        textView.check(matches(withText("YOU SHALL NOT PASS!")));

    }

    /**
     * @author Sebastien Ramsay
     *
     * Tests the login requirements to make sure the test can be passed.
     */
    @Test
    public void testPass() {

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.cityEditText)));


        appCompatEditText.perform(click());


        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.cityEditText)));
        appCompatEditText2.perform(replaceText("Password123#$*"), closeSoftKeyboard());


        ViewInteraction materialButton = onView(allOf(withId(R.id.getForecastButton)));
        materialButton.perform(click());


        ViewInteraction textView = onView(allOf(withId(R.id.instructionsTextView)));
        textView.check(matches(withText("Your password meets the requirements")));

    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
