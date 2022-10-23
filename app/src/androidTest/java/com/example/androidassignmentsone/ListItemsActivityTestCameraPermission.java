package com.example.androidassignmentsone;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ListItemsActivityTestCameraPermission {
    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    @Rule
    public ActivityScenarioRule<LoginActivity> loginActivityActivityScenarioRule = new ActivityScenarioRule<LoginActivity>(LoginActivity.class);
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
        onView(withId(R.id.iAmButton)).perform(click());

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
    public void testImageButtonPrePermission(){
        Bundle bundle = new Bundle();
        Bitmap expectedImage = BitmapFactory.decodeResource(loginActivity.getResources(), R.drawable.camera);
        bundle.putParcelable("data", expectedImage);
        Intent cameraData = new Intent();
        cameraData.putExtras(bundle);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, cameraData);
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);
        onView(withId(R.id.imageButton)).perform(click());
        onView(withId(R.id.imageButton)).check(matches(isDisplayed()));
    }
}
