package com.example.androidassignmentsone;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoginActivityTest {

    @Test
    public void testEmptyEmail(){
        assertFalse(!LoginActivity.validateEmptyEmail(""));
    }

    @Test
    public void testValidateEmail(){
        assertFalse(!LoginActivity.validateInvalidEmail("kishan"));
        assertFalse(!LoginActivity.validateInvalidEmail("kishangp@"));
        assertTrue(!LoginActivity.validateInvalidEmail("kishangp97@gmail.com"));
    }

    @Test
    public void testValidatePassword(){
        assertFalse(!LoginActivity.validatePassword(""));
        assertTrue(!LoginActivity.validatePassword("kishan8140"));
    }
}
