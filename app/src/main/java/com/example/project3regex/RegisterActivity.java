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

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private ImageView showPasswordIcon, showConfirmPasswordIcon;

    // Firebase Authentication instance
    private FirebaseAuth mAuth;

    // Regex patterns
    private static final String NAME_REGEX = "^[a-zA-Z\\s]{2,}$"; // At least 2 characters, alphabets and spaces only
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$"; // Minimum 6 characters, at least one uppercase, one lowercase, one number, and one special character

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
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndRegister();
            }
        });

        // Toggle password visibility for password field
        showPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        // Toggle password visibility for confirm password field
        showConfirmPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleConfirmPasswordVisibility();
            }
        });
    }

    // Toggle visibility of the password in the password field
    private void togglePasswordVisibility() {
        if (passwordEditText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPasswordIcon.setImageResource(android.R.drawable.ic_menu_close_clear_cancel); // "eye open" icon
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPasswordIcon.setImageResource(android.R.drawable.ic_menu_view); // "eye closed" icon
        }
        passwordEditText.setSelection(passwordEditText.getText().length());
    }

    // Toggle visibility of the password in the confirm password field
    private void toggleConfirmPasswordVisibility() {
        if (confirmPasswordEditText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            confirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showConfirmPasswordIcon.setImageResource(android.R.drawable.ic_menu_close_clear_cancel); // "eye open" icon
        } else {
            confirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showConfirmPasswordIcon.setImageResource(android.R.drawable.ic_menu_view); // "eye closed" icon
        }
        confirmPasswordEditText.setSelection(confirmPasswordEditText.getText().length());
    }

    // Validate input fields and register user with Firebase
    private void validateAndRegister() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Check if name is empty or doesn't match the name regex
        if (TextUtils.isEmpty(name) || !name.matches(NAME_REGEX)) {
            nameEditText.setError("Enter a valid name (only letters and spaces, at least 2 characters)");
            return;
        }

        // Validate email
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            return;
        }

        // Validate password with regex
        if (TextUtils.isEmpty(password) || !password.matches(PASSWORD_REGEX)) {
            passwordEditText.setError("Password must be at least 6 characters, including 1 uppercase, 1 lowercase, 1 number, and 1 special character");
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
                        // Registration successful, navigate to the login screen or task page
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        // Optionally, clear the fields after successful registration
                        nameEditText.setText("");
                        emailEditText.setText("");
                        passwordEditText.setText("");
                        confirmPasswordEditText.setText("");

                        // Redirect to another activity (e.g., TaskActivity or LoginActivity)
                        // For example, navigate to LoginActivity:
                        // Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        // startActivity(intent);
                        // finish();
                    } else {
                        // If registration fails, show an error message
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
