package com.example.techupnews;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    TextView newsTitle;
    LinearLayout sportsButton, academicButton, eventsButton;
    ImageView iconSports, iconAcademic, iconEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        newsTitle = findViewById(R.id.newsTitle);
        sportsButton = findViewById(R.id.sportsButton);
        academicButton = findViewById(R.id.academicButton);
        eventsButton = findViewById(R.id.eventsButton);

        iconSports = findViewById(R.id.iconSports);
        iconAcademic = findViewById(R.id.iconAcademic);
        iconEvents = findViewById(R.id.iconEvents);

        // Default selection
        setActiveTab("sports");

        // Click listeners
        sportsButton.setOnClickListener(view -> setActiveTab("sports"));
        academicButton.setOnClickListener(view -> setActiveTab("academic"));
        eventsButton.setOnClickListener(view -> setActiveTab("events"));
    }

    private void setActiveTab(String category) {
        loadFragment(new NewsFragment(category));
        newsTitle.setText(capitalize(category));

        resetTab(iconSports);
        resetTab(iconAcademic);
        resetTab(iconEvents);

        switch (category) {
            case "sports":
                highlightTab(iconSports);
                break;
            case "academic":
                highlightTab(iconAcademic);
                break;
            case "events":
                highlightTab(iconEvents);
                break;
        }
    }

    private void resetTab(ImageView icon) {
        icon.setBackgroundResource(R.drawable.white_icon_square);
        icon.setColorFilter(getColor(R.color.black));
    }

    private void highlightTab(ImageView icon) {
        icon.setBackgroundResource(R.drawable.yellow_icon_square);
        icon.setColorFilter(getColor(R.color.white));
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    private String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
