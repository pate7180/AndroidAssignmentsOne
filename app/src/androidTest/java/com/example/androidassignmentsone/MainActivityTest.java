package com.example.androidassignmentsone;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

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
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> loginActivityActivityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);
    private Activity loginActivity;

    @Before
    public void initMethod(){
        Intents.init();
        String inputText = "kishangp97@gmail.com";
        String password = "kishan8140";
        onView(withId(R.id.editTextLoginName)).perform(
                clearText(), typeText(inputText), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(
                clearText(), typeText(password), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        loginActivityActivityScenarioRule.getScenario()
                .onActivity(new ActivityScenario.ActivityAction<LoginActivity>() {
                    @Override
                    public void perform(LoginActivity activity) {
                        loginActivity = activity;
                    }
                });
    }

    @After
    public void endMethod(){
        Intents.release();
    }

    @Test
    public void testAllElementExist(){
        onView(withId(R.id.welcomeMessage)).check(matches(isDisplayed()));
        onView(withId(R.id.iAmButton)).check(matches(isDisplayed()));
        onView(withId(R.id.startChatButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testIAmButton(){
        onView(withId(R.id.iAmButton)).perform(click());
        onView(withId(R.id.rootListItemsActivity)).check(matches(isDisplayed()));
    }

    @Test
    public void testStartChatButton(){
        onView(withId(R.id.startChatButton)).perform(click());
        onView(withId(R.id.rootChatWindowActivity)).check(matches(isDisplayed()));
    }

    @Test
    public void testMainActivityValid(){
        onView(withId(R.id.iAmButton)).perform(click());
        String snackBarMessageString = loginActivity.getResources().getString(R.string.print_function_text);
        onView(withText(snackBarMessageString))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.checkBox)).perform(click());
        onView(withText(loginActivity.getResources().getString(R.string.yes))).perform(click());
        onView(withText(R.string.response_text))
                .inRoot(withDecorView(not(loginActivity.getWindow().getDecorView())))
                .check(matches(withText(R.string.response_text)));
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMainActivityInvalid(){
        Intent mockIntent = new Intent();
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_CANCELED, mockIntent);
        intending(hasComponent(ListItemsActivity.class.getName())).respondWith(result);
        onView(withId(R.id.iAmButton)).perform(click());
        onView(withId(R.id.welcomeMessage)).check(matches(isDisplayed()));
    }
}