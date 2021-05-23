package edu.gcit.todo_4;

import android.content.Context;

import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public static final String message = "this is a test";
    public static final String reply = "this is a reply";

        @Rule
        public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

        @Test
        public void useAppContext() {

            // Context of the app under test.
            Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            assertEquals("edu.gcit.todo_4", appContext.getPackageName());
        }
        @Test
        public void activityLaunch() {
            onView(withId(R.id.Sendbutton)).perform(click());
            onView(withId(R.id.replyReceivedMainactivity2)).check(matches(isDisplayed()));
            onView(withId(R.id.buttonReply)).perform(click());
            onView(withId(R.id.replyReceivedMainactivity)).check(matches(isDisplayed()));
        }

        @Test
        public void textInputOutput() {
            onView(withId(R.id.editTextMessage1)).perform(typeText(message),closeSoftKeyboard());
            onView(withId(R.id.Sendbutton)).perform(click());
            onView(withId(R.id.editTextMessage2)).perform(typeText(reply),closeSoftKeyboard());
            onView(withId(R.id.buttonReply)).perform(click());
        }
    }
