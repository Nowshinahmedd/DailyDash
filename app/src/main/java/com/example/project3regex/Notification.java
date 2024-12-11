package com.example.project3regex;  // Replace with your app's package name

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Notification extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch notificationSwitch;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification); // Replace with your XML layout file

        // Initialize the Switch and SharedPreferences
        notificationSwitch = findViewById(R.id.notificationSwitch);
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);

        // Retrieve the saved state of the switch (default is false)
        boolean isNotificationsEnabled = sharedPreferences.getBoolean("isNotificationsEnabled", false);
        notificationSwitch.setChecked(isNotificationsEnabled);

        // Set an OnCheckedChangeListener to listen for changes in the switch's state
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // When the switch is ON (notifications enabled)
                Toast.makeText(Notification.this, "Notifications Enabled", Toast.LENGTH_SHORT).show();
            } else {
                // When the switch is OFF (notifications disabled)
                Toast.makeText(Notification.this, "Notifications Disabled", Toast.LENGTH_SHORT).show();
            }

            // Save the state of the switch to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isNotificationsEnabled", isChecked);
            editor.apply(); // Apply the changes asynchronously
        });
    }
}
