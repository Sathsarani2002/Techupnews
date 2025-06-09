package com.example.techupnews;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    TextView newsTitle;
    ImageView iconMenu, iconSports, iconAcademic, iconEvents, iconHome;

    ViewPager2 imageSlider;
    int[] images = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6
    };

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

        // Set default screen title
        newsTitle.setText("Sports");

        // Set click listeners
        iconMenu.setOnClickListener(v -> showUserProfileBottomSheet());
        iconSports.setOnClickListener(v -> newsTitle.setText("Sports"));
        iconAcademic.setOnClickListener(v -> newsTitle.setText("Academic"));
        iconEvents.setOnClickListener(v -> newsTitle.setText("Events"));
        iconHome.setOnClickListener(v -> newsTitle.setText("Sports"));

        // Setup ViewPager2
        imageSlider = findViewById(R.id.imageSlider);
        imageSlider.setAdapter(new CarouselAdapter(this, images));
        imageSlider.setClipToPadding(false);
        imageSlider.setClipChildren(false);
        imageSlider.setOffscreenPageLimit(3);
        imageSlider.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        // Carousel margin and scale
        float pageMarginPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        float offsetPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

        imageSlider.setPageTransformer((page, position) -> {
            float translationX = -position * offsetPx;
            page.setTranslationX(translationX);
            float scale = 1 - 0.1f * Math.abs(position);
            page.setScaleY(scale);
            page.setScaleX(scale);
        });
    }

    private void showUserProfileBottomSheet() {
        UserProfileBottomSheet bottomSheet = new UserProfileBottomSheet();
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
    }
}
