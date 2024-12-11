package com.example.project3regex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Ensure this XML file is named activity_main.xml

        // Initialize buttons
        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);
        Button calendarButton = findViewById(R.id.calendarButton);
        Button taskListButton = findViewById(R.id.taskListButton);
        Button notificationButton = findViewById(R.id.notificationButton);

        // Set up button listeners
        loginButton.setOnClickListener(v -> {
            // Open Login Activity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(v -> {
            // Open Register Activity
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        calendarButton.setOnClickListener(v -> {
            // Open Calendar Activity
            Intent intent = new Intent(MainActivity.this, Calender.class);
            startActivity(intent);
        });

        taskListButton.setOnClickListener(v -> {
            // Open Task List Activity
            Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
            startActivity(intent);
        });

        notificationButton.setOnClickListener(v -> {
            // Open Notification Settings Activity
            Intent intent = new Intent(MainActivity.this, Notification.class);
            startActivity(intent);
        });
    }
}
