package com.example.project3regex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

/** @noinspection deprecation*/
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);  // Ensure this matches your splash layout XML file

        // Handler to delay the transition to LoginActivity for 2 seconds
        new Handler().postDelayed(() -> {
            // Start the LoginActivity after splash screen
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();  // Close SplashActivity to remove it from the back stack
        }, 2000);  // 2 seconds delay
    }
}
