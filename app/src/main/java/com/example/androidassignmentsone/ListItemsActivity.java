package com.example.androidassignmentsone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ListItemsActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    private ImageButton imageButton;
    private static final int MY_CAMERA_REQUEST_CODE = 101;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        print(getResources().getString(R.string.print_function_text));

        //Image Capture permission and take photo logic
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    Log.i(ACTIVITY_NAME, "CAMERA PERMISSION REQUESTED");
                }else{
                    Log.i(ACTIVITY_NAME, "ALREADY CAMERA PERMISSION GIVEN");
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
                }
            }
        });


        //Switch On-Off logic
        Switch aSwitch = findViewById(R.id.switchButton);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.switch_on), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.switch_off), Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Show dialog on checkbox click
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(getResources().getString(R.string.dailog_alert));
        builder.setMessage(getResources().getString(R.string.dailog_message));
        builder.setCancelable(true);

        builder.setPositiveButton(
                getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("Response", getResources().getString(R.string.response_text));
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                });

        builder.setNegativeButton(
                getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        CheckBox checkbox = findViewById(R.id.checkBox);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertDialog alert = builder.create();
                alert.show();
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getResources().getString(R.string.camera_permission_granted), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
            } else {
                Toast.makeText(this, getResources().getString(R.string.camera_permission_denied), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == MY_CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageButton.setImageBitmap(photo);
        }
    }


    protected void print(String snackBarMessage){
        Snackbar snack = Snackbar.make(findViewById(R.id.rootListItemsActivity), snackBarMessage, Snackbar.LENGTH_LONG);
        snack.show();
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
}