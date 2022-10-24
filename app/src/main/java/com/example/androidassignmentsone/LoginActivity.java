package com.example.androidassignmentsone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    EditText editTextLoginName, editTextPassword;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        String defaultEmail =  sharedPreferences.getString("DefaultEmail", "email@domain.com");

        editTextLoginName = findViewById(R.id.editTextLoginName);
        editTextLoginName.setText(defaultEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void login(View view){
        String loginName = String.valueOf(editTextLoginName.getText());
        String password = String.valueOf(editTextPassword.getText());


        if(validateEmptyEmail(loginName)){
            Toast.makeText(getApplicationContext() , getResources().getString(R.string.login_name_required) ,Toast.LENGTH_SHORT).show();
        }else if(validateInvalidEmail(loginName)){
            Toast.makeText(getApplicationContext() ,getResources().getString(R.string.login_name_invalid),Toast.LENGTH_SHORT).show();
        } else if(validatePassword(password)){
            Toast.makeText(getApplicationContext() ,getResources().getString(R.string.password_required),Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onResume(){
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }

    @Override
    protected void onStart(){
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
    }

    @Override
    protected void onPause(){
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    protected void onStop(){
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
    }

    public static boolean validateEmptyEmail(String loginName){
        return loginName.matches("");
    }

    public static boolean validateInvalidEmail(String loginName) {
        return !PatternsCompat.EMAIL_ADDRESS.matcher(loginName).matches();
    }

    public static boolean validatePassword(String password){
        return password.matches("");
    }

}