package com.example.androidassignmentsone;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidassignmentsone.databinding.ActivityTestToolbarBinding;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding activityTestToolbarBinding;
    private String newToastNotificationMessageForOption1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTestToolbarBinding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(activityTestToolbarBinding.getRoot());

        setSupportActionBar(activityTestToolbarBinding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        activityTestToolbarBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.test_toolbar_letter_button_text, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        newToastNotificationMessageForOption1= getString(R.string.test_toolbar_option_one_snackbar_text);

    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
        return true;
    }

    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();
        switch (id){
            case R.id.action_one:
                Log.d("Toolbar", newToastNotificationMessageForOption1);
                Snackbar.make(activityTestToolbarBinding.getRoot(), newToastNotificationMessageForOption1, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.action_two:
                //Start an activity…
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.test_toolbar_alert_msg);
                // Add the buttons
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.action_three:
                LayoutInflater inflater = LayoutInflater.from(TestToolbar.this);
                final View customView = inflater.inflate(R.layout.test_toolbar_cutom_layout, null);
                final TextView newMessage = (EditText) customView.findViewById(R.id.new_notification_toast_msg);
                newMessage.setText(newToastNotificationMessageForOption1);
                android.app.AlertDialog.Builder dialog2 = new android.app.AlertDialog.Builder(TestToolbar.this)
                        .setTitle(R.string.test_toolbar_cutom_alert_msg)
                        .setView(customView)
                        .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog2, int whichButton) {

                                if(!newToastNotificationMessageForOption1.equals(newMessage.getText().toString())){
                                    newToastNotificationMessageForOption1 = newMessage.getText().toString();
                                    Toast.makeText(TestToolbar.this,R.string.test_toolbar_cutom_update_msg_toast,Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog2, int id) {
                                // User cancelled the dialog
                            }
                        });
                dialog2.show();
                break;

            case R.id.about:
                //Start an activity…
                Toast.makeText(this, R.string.test_toolbar_about_msg, Toast.LENGTH_LONG).show();
                break;
        }

        return true;
    }
}