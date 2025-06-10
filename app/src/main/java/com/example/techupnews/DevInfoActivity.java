package com.example.techupnews;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DevInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devinfo);

        // Close button functionality
        ImageView closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> navigateToMainActivity());
    }

    @Override
    public void onBackPressed() {
        navigateToMainActivity(); // Go back to the MainActivity when back button is pressed
    }

    private void navigateToMainActivity() {
        // Navigate to MainActivity ensuring it is in the correct state
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

        // Finish the current activity
        finish();
    }
}
