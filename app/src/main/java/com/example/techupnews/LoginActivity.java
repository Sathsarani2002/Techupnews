package com.example.techupnews;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnGoSignup, loginButton;
    EditText loginUsername, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        btnLogin = findViewById(R.id.btnLogin);
        btnGoSignup = findViewById(R.id.btnGoSignup);
        loginButton = findViewById(R.id.loginButton);
        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);

        // Highlight the login button
        btnLogin.setSelected(true);
        btnGoSignup.setSelected(false);

        // Navigate to SignupActivity when "Go to Signup" is clicked
        btnGoSignup.setOnClickListener(v -> {
            btnGoSignup.setSelected(true);
            btnLogin.setSelected(false);
            startActivity(new Intent(this, SignupActivity.class));
            finish();
        });

        // Perform login action when the login button is clicked
        loginButton.setOnClickListener(v -> {
            String username = loginUsername.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                loginUsername.setError("Fields cannot be empty!");
                loginPassword.setError("Fields cannot be empty!");
                return;
            }

            // Add further login validation here
            // For now, assume successful login
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
