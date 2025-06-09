package com.example.techupnews;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class DevInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devinfo);

        // Close button functionality
        ImageView closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> finishWithBottomSheet());
    }

    @Override
    public void onBackPressed() {
        finishWithBottomSheet(); // Move back to the UserProfileBottomSheet when back button is pressed
    }

    private void finishWithBottomSheet() {
        // Show the UserProfileBottomSheet before finishing
        FragmentManager fragmentManager = getSupportFragmentManager();
        UserProfileBottomSheet userProfileBottomSheet = new UserProfileBottomSheet();
        userProfileBottomSheet.show(fragmentManager, userProfileBottomSheet.getTag());

        // Finish the current activity
        finish();
    }
}
