package com.example.techupnews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText usernameEditText, emailEditText, passwordEditText;
    private ImageView profileImage;
    private DatabaseHelper databaseHelper;
    private Uri selectedImageUri;
    private boolean isPasswordVisible = false;

    private String currentUsername; // To store logged-in user before editing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        databaseHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        profileImage = findViewById(R.id.profileImage);
        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton closeButton = findViewById(R.id.closeButton);
        ImageButton changeProfileImageButton = findViewById(R.id.changeProfileImageButton);
        ImageButton togglePasswordButton = findViewById(R.id.togglePasswordVisibilityButton);

        loadUserData();

        backButton.setOnClickListener(v -> finish());
        closeButton.setOnClickListener(v -> resetFields());
        changeProfileImageButton.setOnClickListener(v -> openImagePicker());
        togglePasswordButton.setOnClickListener(v -> togglePasswordVisibility());
        findViewById(R.id.saveButton).setOnClickListener(v -> saveChanges());
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isPasswordVisible = false;
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isPasswordVisible = true;
        }
        passwordEditText.setSelection(passwordEditText.length());
    }

    private void loadUserData() {
        currentUsername = databaseHelper.getLoggedInUser(this);
        if (currentUsername == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String[] userDetails = databaseHelper.getUserDetails(currentUsername);
        if (userDetails != null) {
            usernameEditText.setText(userDetails[0]);
            emailEditText.setText(userDetails[1]);

            // Preload the password
            String password = databaseHelper.getPassword(currentUsername);
            if (password != null) {
                passwordEditText.setText(password);
            }
        } else {
            Toast.makeText(this, "Failed to load user data!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveChanges() {
        String newUsername = usernameEditText.getText().toString().trim();
        String newEmail = emailEditText.getText().toString().trim();
        String newPassword = passwordEditText.getText().toString().trim();

        if (newUsername.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean updated = databaseHelper.updateUserDetails(currentUsername, newUsername, newEmail, newPassword);
        if (updated) {
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            currentUsername = newUsername;  // Update currentUsername if username changed
            finish();
        } else {
            Toast.makeText(this, "Failed to update profile!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFields() {
        loadUserData();
        Toast.makeText(this, "Changes discarded.", Toast.LENGTH_SHORT).show();
    }
}
