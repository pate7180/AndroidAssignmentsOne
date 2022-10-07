package com.example.androidassignmentsone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "MainActivity";
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.iAmButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "I am button clicked");
                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent,10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        super.onActivityResult(requestCode,responseCode,data);
        Log.i(ACTIVITY_NAME, "Returned to MainActivity onActivityResult" );
        if(requestCode == 10){
            if(responseCode == Activity.RESULT_OK) {
                String messagePassed = data.getStringExtra("Response");
                Toast.makeText(this, messagePassed, Toast.LENGTH_LONG).show();
            }else{
                Log.i(ACTIVITY_NAME, "Invalid responseCode" );
            }
        }else{
            Log.i(ACTIVITY_NAME, "Invalid requestCode" );
        }
    }

    @Override
    protected void onResume(){
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }

    @Override
    protected void onStart(){
        Log.i(ACTIVITY_NAME,"In onStart()");
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


}