package com.example.techupnews;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    Button btnSignup, btnGoLogin, signupButton;
    EditText signupUsername, signupEmail, signupPassword, signupConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        btnSignup = findViewById(R.id.btnSignup);
        btnGoLogin = findViewById(R.id.btnGoLogin);
        signupButton = findViewById(R.id.signupButton);
        signupUsername = findViewById(R.id.signupUsername);
        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        signupConfirm = findViewById(R.id.signupConfirm);

        // Highlight the signup button
        btnSignup.setSelected(true);
        btnGoLogin.setSelected(false);

        // Navigate to LoginActivity when "Go to Login" is clicked
        btnGoLogin.setOnClickListener(v -> {
            btnGoLogin.setSelected(true);
            btnSignup.setSelected(false);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // Show a message when the signup button is clicked
        signupButton.setOnClickListener(v -> {
            String username = signupUsername.getText().toString().trim();
            String email = signupEmail.getText().toString().trim();
            String password = signupPassword.getText().toString().trim();
            String confirm = signupConfirm.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirm)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show();
        });
    }
}
