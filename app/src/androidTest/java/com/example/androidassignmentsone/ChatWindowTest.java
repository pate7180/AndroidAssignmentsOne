package com.example.androidassignmentsone;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import android.app.Activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ChatWindowTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> loginActivityActivityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<MainActivity>(MainActivity.class);
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
        onView(withId(R.id.startChatButton)).perform(click());

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
    public void chatTestMethod() {
        onView(withId(R.id.editTextChatMessage))
                .perform(click(), typeText("How are you?"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.sendButton)).perform(click());
        onView(withId(R.id.editTextChatMessage)).perform(clearText());

        onView(withId(R.id.editTextChatMessage))
                .perform(click(), typeText("I am fine, what about you"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.sendButton)).perform(click());
        onView(withId(R.id.editTextChatMessage)).perform(clearText());

        onView(withText("How are you?")).check(matches(isDisplayed()));
        onView(withText("I am fine, what about you")).check(matches(isDisplayed()));

        onView(withId(R.id.rootChatWindowActivity))
               .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.rootChatWindowActivity))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void evenAfterClickingBackButtonPreviousChatExistsTestMethod(){
        Random rand = new Random();
        int upperbound = 1000000000;
        int randomNumber = rand.nextInt(upperbound);
        String newMessage= "Demo Message"+ randomNumber;

        onView(withId(R.id.editTextChatMessage)).perform(clearText(),ViewActions.typeText(newMessage));
        onView(withId(R.id.sendButton)).perform(ViewActions.click());
        onView(withContentDescription("Navigate up")).perform(ViewActions.click());
        testRule.launchActivity(null);
        onView(withId(R.id.startChatButton)).perform(ViewActions.click());
        onView(withText(newMessage)).check(matches(isDisplayed()));
    }
}