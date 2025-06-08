package com.example.techupnews;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.news_screen.R;

public class MainActivity extends AppCompatActivity {

    ImageView iconMenu, iconHome, iconSports, iconAcademic, iconEvents;
    TextView newsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        iconMenu = findViewById(R.id.iconMenu);
        iconHome = findViewById(R.id.iconHome);
        iconSports = findViewById(R.id.iconSports);
        iconAcademic = findViewById(R.id.iconAcademic);
        iconEvents = findViewById(R.id.iconEvents);
        newsTitle = findViewById(R.id.newsTitle);

        // Set initial title to "Sports"
        newsTitle.setText("Sports");

        // Set click listeners for bottom nav icons
        iconHome.setOnClickListener(v -> newsTitle.setText("Sports"));
        iconSports.setOnClickListener(v -> newsTitle.setText("Sports"));
        iconAcademic.setOnClickListener(v -> newsTitle.setText("Academic"));
        iconEvents.setOnClickListener(v -> newsTitle.setText("Events"));

        // Show user profile bottom sheet when menu icon is clicked
        iconMenu.setOnClickListener(v -> {
            UserProfileBottomSheet bottomSheet = new UserProfileBottomSheet();
            bottomSheet.show(getSupportFragmentManager(), "UserProfileBottomSheet");
        });
    }
}