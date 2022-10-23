package com.example.androidassignmentsone;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import android.app.Activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> loginActivityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    private Activity myActivity;

    @Before
    public void initMethod(){
        Intents.init();
        loginActivityScenarioRule.getScenario().onActivity(new ActivityScenario.ActivityAction<LoginActivity>() {
            @Override
            public void perform(LoginActivity activity) {
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
    public void testEmptyEmail(){
        String invalidEmail = "";
        String errorMessage = myActivity.getResources().getString(R.string.login_name_required);
        onView(withId(R.id.editTextLoginName)).perform(clearText(), typeText(invalidEmail));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withText(errorMessage))
                .inRoot(withDecorView(not(myActivity.getWindow().getDecorView())))
                .check(matches(withText(errorMessage)));
    }

    @Test
    public void testInvalidEmail(){
        String invalidEmail = "kishan";
        String errorMessage = myActivity.getResources().getString(R.string.login_name_invalid);
        onView(withId(R.id.editTextLoginName)).perform(clearText(), typeText(invalidEmail));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withText(errorMessage))
                .inRoot(withDecorView(not(myActivity.getWindow().getDecorView())))
                .check(matches(withText(errorMessage)));
    }

    @Test
    public void testEmptyPassword(){
        String invalidEmail = "";
        String errorMessage = myActivity.getResources().getString(R.string.password_required);
        onView(withId(R.id.editTextPassword)).perform(clearText(), typeText(invalidEmail));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withText(errorMessage))
                .inRoot(withDecorView(not(myActivity.getWindow().getDecorView())))
                .check(matches(withText(errorMessage)));
    }

    @Test
    public void testSuccessLogin(){
        String inputText = "kishangp97@gmail.com";
        String password = "kishan8140";
        onView(withId(R.id.editTextLoginName)).perform(clearText());
        onView(withId(R.id.editTextPassword)).perform(clearText());
        onView(withId(R.id.editTextLoginName)).perform(
                typeText(inputText), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(
                typeText(password), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextLoginName)).perform(click());
        onView(withId(R.id.loginButton)).perform(click());
        Intents.intended(hasComponent(MainActivity.class.getName()));
    }
}