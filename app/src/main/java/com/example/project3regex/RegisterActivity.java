package com.example.project3regex;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private ImageView showPasswordIcon, showConfirmPasswordIcon;

    // Firebase Authentication instance
    private FirebaseAuth mAuth;

    // Regex patterns
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z ]{1,49}$");
    // Starts with a letter, allows spaces, and is between 2-50 characters

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    // At least one digit, one uppercase, one lowercase, one special character, no spaces, and min 8 characters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        registerButton = findViewById(R.id.register_button);
        showPasswordIcon = findViewById(R.id.show_password_icon);
        showConfirmPasswordIcon = findViewById(R.id.show_confirm_password_icon);

        // Set onClickListener for the register button
        registerButton.setOnClickListener(v -> validateAndRegister());

        // Toggle password visibility for password field
        showPasswordIcon.setOnClickListener(v -> togglePasswordVisibility());

        // Toggle password visibility for confirm password field
        showConfirmPasswordIcon.setOnClickListener(v -> toggleConfirmPasswordVisibility());
    }

    // Toggle visibility of the password in the password field
    private void togglePasswordVisibility() {
        toggleVisibility(passwordEditText, showPasswordIcon);
    }

    // Toggle visibility of the password in the confirm password field
    private void toggleConfirmPasswordVisibility() {
        toggleVisibility(confirmPasswordEditText, showConfirmPasswordIcon);
    }

    private void toggleVisibility(EditText editText, ImageView icon) {
        if (editText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            icon.setImageResource(android.R.drawable.ic_menu_close_clear_cancel); // "eye open" icon
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            icon.setImageResource(android.R.drawable.ic_menu_view); // "eye closed" icon
        }
        editText.setSelection(editText.getText().length());
    }

    // Validate input fields and register user with Firebase
    private void validateAndRegister() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Check if name is empty or doesn't match the NAME_PATTERN
        if (TextUtils.isEmpty(name) || !NAME_PATTERN.matcher(name).matches()) {
            nameEditText.setError("Enter a valid name (letters and spaces, 2-50 characters)");
            return;
        }

        // Validate email using built-in Patterns.EMAIL_ADDRESS
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            return;
        }

        // Validate password with PASSWORD_PATTERN
        if (TextUtils.isEmpty(password) || !PASSWORD_PATTERN.matcher(password).matches()) {
            passwordEditText.setError("Password must be at least 8 characters, include 1 uppercase, 1 lowercase, 1 number, and 1 special character");
            return;
        }

        // Validate confirm password
        if (TextUtils.isEmpty(confirmPassword) || !password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return;
        }

        // Register user with Firebase Authentication
        registerWithFirebase(email, password);
    }

    // Firebase registration method
    private void registerWithFirebase(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                        // Optionally, clear the fields after successful registration
                        clearFields();

                        // Redirect to another activity (e.g., LoginActivity)
                        // Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        // startActivity(intent);
                        // finish();
                    } else {
                        // If registration fails, show an error message
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Clear all input fields
    private void clearFields() {
        nameEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");
        confirmPasswordEditText.setText("");
    }
}
