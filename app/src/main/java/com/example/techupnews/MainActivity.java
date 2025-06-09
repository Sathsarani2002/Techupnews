package com.example.techupnews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    TextView newsTitle;
    ImageView iconMenu, iconSports, iconAcademic, iconEvents, iconHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        newsTitle = findViewById(R.id.newsTitle);

        iconMenu = findViewById(R.id.iconMenu);
        iconSports = findViewById(R.id.iconSports);
        iconAcademic = findViewById(R.id.iconAcademic);
        iconEvents = findViewById(R.id.iconEvents);
        iconHome = findViewById(R.id.iconHome);

        // Set default title
        newsTitle.setText("Sports");

        // Set click listeners
        iconMenu.setOnClickListener(v -> showUserProfileBottomSheet());

        iconSports.setOnClickListener(v -> newsTitle.setText("Sports"));
        iconAcademic.setOnClickListener(v -> newsTitle.setText("Academic"));
        iconEvents.setOnClickListener(v -> newsTitle.setText("Events"));
        iconHome.setOnClickListener(v -> newsTitle.setText("Sports"));
    }

    private void showUserProfileBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = LayoutInflater.from(this).inflate(R.layout.user_profile_bottom_sheet, null);
        bottomSheetDialog.setContentView(sheetView);

        ImageView closeBtn = sheetView.findViewById(R.id.closeButton);
        closeBtn.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
    }
}
