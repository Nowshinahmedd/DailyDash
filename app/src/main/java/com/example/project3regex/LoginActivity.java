package com.example.project3regex;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;
    private TextView dontHaveAccountText;

    // Firebase Authentication instance
    private FirebaseAuth mAuth;

    // Regex for email and password validation
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$"; // Simple email regex
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$"; // Password regex: 6+ characters, 1 uppercase, 1 lowercase, 1 number, 1 special character

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        dontHaveAccountText = findViewById(R.id.dont_have_account);

        // Login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate input with regex
                if (isValidEmail(email) && isValidPassword(password)) {
                    // Attempt to log in with Firebase
                    loginWithFirebase(email, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Register button click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // "Don't have an account? Register" text click listener
        dontHaveAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // Email validation using regex
    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && email.matches(EMAIL_REGEX);
    }

    // Password validation using regex
    private boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.matches(PASSWORD_REGEX);
    }

    // Firebase login method
    private void loginWithFirebase(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful, navigate to the next activity
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        // Proceed to the task page
                        Intent intent = new Intent(LoginActivity.this, TaskActivity.class);
                        startActivity(intent);
                        finish();  // Close login activity to prevent returning to it
                    } else {
                        // If login fails, display an error message
                        Toast.makeText(LoginActivity.this, "Authentication Failed. Please check your email and password.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
