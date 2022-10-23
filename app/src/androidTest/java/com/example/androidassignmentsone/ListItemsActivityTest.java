package com.example.androidassignmentsone;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

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
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ListItemsActivityTest {
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

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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
    public void testSwitchOnAndOff(){
        String toastMessage = loginActivity.getResources().getString(R.string.switch_on);
        onView(withId(R.id.switchButton)).perform(click());
        onView(withText(toastMessage))
                .inRoot(withDecorView(not(loginActivity.getWindow().getDecorView())))
                .check(matches(withText(toastMessage)));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        toastMessage = loginActivity.getResources().getString(R.string.switch_off);
        onView(withId(R.id.switchButton)).perform(click());
        onView(withText(toastMessage))
                .inRoot(withDecorView(not(loginActivity.getWindow().getDecorView())))
                .check(matches(withText(toastMessage)));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckBoxCancel(){
        onView(withId(R.id.checkBox)).perform(click());
        onView(withText(loginActivity.getResources().getString(R.string.no))).perform(click());

        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckBoxApprove(){
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
    public void testImageButtonGivePermission(){

        Bundle bundle = new Bundle();
        Bitmap expectedImage = BitmapFactory.decodeResource(
                loginActivity.getResources(), R.drawable.camera);
        bundle.putParcelable("data", expectedImage);
        Intent cameraData = new Intent();
        cameraData.putExtras(bundle);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, cameraData);
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);
        onView(withId(R.id.imageButton)).perform(click());

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject allowPermission = device.findObject(new UiSelector().text("WHILE USING THE APP"));
        if (allowPermission.exists()){
            try {
                allowPermission.click();
            }
            catch (UiObjectNotFoundException e){
                e.printStackTrace();
            }
        }
        onView(withId(R.id.imageButton)).check(matches(isDisplayed()));

    }

    @Test
    public void testImageButtonDenyPermission(){
        onView(withId(R.id.imageButton)).perform(click());

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject allowPermission = device.findObject(new UiSelector().text("DENY"));
        if (allowPermission.exists()){
            try {
                allowPermission.click();
            }
            catch (UiObjectNotFoundException e){
                e.printStackTrace();
            }
        }
        String toastMessage = "Camera permission denied";
        onView(withText(toastMessage))
                .inRoot(withDecorView(not(loginActivity.getWindow().getDecorView())))
                .check(matches(withText(toastMessage)));
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}