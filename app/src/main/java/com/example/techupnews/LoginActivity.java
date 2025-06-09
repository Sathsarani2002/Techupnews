package com.example.techupnews;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btnLoginToggle, btnSignupToggle, loginButton;
    private EditText loginUsername, loginPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        // Initialize views
        btnLoginToggle = findViewById(R.id.btnLogin);
        btnSignupToggle = findViewById(R.id.btnGoSignup);
        loginButton = findViewById(R.id.loginButton);
        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);

        // Highlight login tab
        btnLoginToggle.setSelected(true);
        btnSignupToggle.setSelected(false);

        // Toggle to SignupActivity
        btnSignupToggle.setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
            finish();
        });

        // Login button logic
        loginButton.setOnClickListener(v -> {
            String username = loginUsername.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username)) {
                loginUsername.setError("Username is required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                loginPassword.setError("Password is required");
                return;
            }

            boolean isValid = dbHelper.checkUserCredentials(username, password);
            if (isValid) {
                dbHelper.saveLoggedInUser(this, username);
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
