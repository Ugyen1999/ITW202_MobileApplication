package com.gcit.todo4;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

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
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ActivityInputOutputTest {
   // public static final String message = "This is a test";
   // public  static final String reply = "This is a reply";
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.gcit.todo4", appContext.getPackageName());
    }
    @Test
    public void activityLaunch(){
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.Reply_button)).perform((click()));
        onView(withId(R.id.Received_Reply)).check(matches(isDisplayed()));
    }
    @Test
    public void textInputOutput(){
        onView(withId(R.id.editText1)).perform(typeText("This is a text"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView2)).check(matches(withText("This is a text")));

        onView(withId(R.id.MessageReply)).perform(typeText("This is a reply"));
        onView(withId(R.id.Reply_button)).perform(click());
        onView(withId(R.id.Replied_Message)).check(matches(withText("This is a reply")));
    }
}