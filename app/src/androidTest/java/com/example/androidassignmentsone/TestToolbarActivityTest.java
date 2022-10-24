package com.example.androidassignmentsone;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.Matchers.not;

import android.app.Activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestToolbarActivityTest {

    @Rule
    public ActivityScenarioRule<TestToolbar> TestToolbarScenarioRule = new ActivityScenarioRule<>(TestToolbar.class);
    private Activity myActivity;
    public String newToastNotificationMessageForOption1;

    @Before
    public void initMethod(){
        Intents.init();
        TestToolbarScenarioRule.getScenario().onActivity(new ActivityScenario.ActivityAction<TestToolbar>() {
            @Override
            public void perform(TestToolbar activity) {
                myActivity = activity;
            }
        });
    }

    @After
    public void endMethod(){
        Intents.release();
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAllToolbarUiIsDisplayed() {
        onView(withId(R.id.action_one)).
                check(matches(isDisplayed()));
        onView(withId(R.id.action_two)).
                check(matches(isDisplayed()));
        onView(withId(R.id.action_three)).
                check(matches(isDisplayed()));
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
    }

    @Test
    public void testEmailLetterButtonClick(){
        onView(withId(R.id.fab)).perform(ViewActions.click());
        onView(withText(R.string.test_toolbar_letter_button_text))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void testAboutButtonClick(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText(R.string.test_toolbar_about)).perform(ViewActions.click());
//        onView(withText(R.string.version1))
//                .inRoot(new ToastMatcher())
//                .check(matches(isDisplayed()));

        onView(withText(R.string.test_toolbar_about_msg))
                .inRoot(withDecorView(not(myActivity.getWindow().getDecorView())))
                .check(matches(withText(R.string.test_toolbar_about_msg)));

    }

    @Test
    public void testActionOneClick(){
        onView(withId(R.id.action_one)).
                perform(ViewActions.click());
        onView(withText(R.string.test_toolbar_option_one_snackbar_text))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testActionTwoClickAndPerformYes(){
        onView(withId(R.id.action_two)).
                perform(ViewActions.click());
        onView(withText(R.string.yes)).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(ViewActions.click());
    }

    @Test
    public void testActivityTwoClickAndPerformNo(){
        onView(withId(R.id.action_two)).
                perform(ViewActions.click());
        onView(withText(R.string.no)).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(ViewActions.click());
    }

    @Test
    public void testActivityThreeClickAndPerformUpdate(){
        onView(withId(R.id.action_three)).
                perform(ViewActions.click());
        onView(withText(R.string.update)).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(ViewActions.click());
    }

    @Test
    public void testActivityThreeClickAndUpdateActivityOneNotificationText(){
        onView(withId(R.id.action_three)).
                perform(ViewActions.click());

        newToastNotificationMessageForOption1=myActivity.getResources().getString(R.string.welcome_message);

        onView(withId(R.id.new_notification_toast_msg)).
                perform(clearText(),ViewActions.typeText(newToastNotificationMessageForOption1));
        onView(withText(R.string.update)).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(ViewActions.click());
        // check if toast is displayed for notification changed
//        onView(withText(R.string.test_toolbar_cutom_update_msg_toast))
//                .inRoot(new ToastMatcher())
//                .check(matches(isDisplayed()));

        onView(withText(R.string.test_toolbar_cutom_update_msg_toast))
                .inRoot(withDecorView(not(myActivity.getWindow().getDecorView())))
                .check(matches(withText(R.string.test_toolbar_cutom_update_msg_toast)));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.action_one)).perform(ViewActions.click());
        // check if new notification message has been set on option 1 click
        onView(withText(newToastNotificationMessageForOption1))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }

    @Test
    public void testActivityThreeClickAndPerformCancel(){
        onView(withId(R.id.action_three)).
                perform(ViewActions.click());
        onView(withText(R.string.cancel)).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(ViewActions.click());
    }

}
