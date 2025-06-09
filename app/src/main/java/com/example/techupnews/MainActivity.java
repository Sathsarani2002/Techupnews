package com.example.techupnews;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    TextView newsTitle;
    ImageView iconMenu, iconSports, iconAcademic, iconEvents, iconHome;
    ViewPager2 imageSlider;
    TabLayout tabIndicator;

    int[] images = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6
    };

    private Handler sliderHandler;
    private FirebaseAnalytics firebaseAnalytics; // Firebase Analytics instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        logFirebaseAnalyticsEvent();

        // Initialize views
        newsTitle = findViewById(R.id.newsTitle);
        iconMenu = findViewById(R.id.iconMenu);
        iconSports = findViewById(R.id.iconSports);
        iconAcademic = findViewById(R.id.iconAcademic);
        iconEvents = findViewById(R.id.iconEvents);
        iconHome = findViewById(R.id.iconHome);
        imageSlider = findViewById(R.id.imageSlider);
        tabIndicator = findViewById(R.id.tabIndicator);

        // Set up ViewPager2
        imageSlider.setAdapter(new CarouselAdapter(this, images));
        configureViewPager();

        // Set up TabLayout with ViewPager2 for dot indicators
        new TabLayoutMediator(tabIndicator, imageSlider, (tab, position) -> {
            // No specific tab titles required, just dots
        }).attach();

        // Set default title
        newsTitle.setText("Sports");

        // Set click listeners
        iconMenu.setOnClickListener(v -> showUserProfileBottomSheet());
        iconSports.setOnClickListener(v -> newsTitle.setText("Sports"));
        iconAcademic.setOnClickListener(v -> newsTitle.setText("Academic"));
        iconEvents.setOnClickListener(v -> newsTitle.setText("Events"));
        iconHome.setOnClickListener(v -> newsTitle.setText("Sports"));

        // Initialize the Handler for auto-scrolling
        sliderHandler = new Handler(Looper.getMainLooper());
        startAutoScroll();
    }

    private void configureViewPager() {
        imageSlider.setClipToPadding(false);
        imageSlider.setClipChildren(false);
        imageSlider.setOffscreenPageLimit(3);
        imageSlider.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        int pageMargin = 16; // Margin between pages (in dp)
        int offset = 50; // Offset to show part of adjacent pages
        float density = getResources().getDisplayMetrics().density;

        imageSlider.setPageTransformer((page, position) -> {
            float translationX = -(offset * density) * position;
            if (imageSlider.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                page.setTranslationX(translationX);
            }
            // Add page margin and slight scaling effect
            float scaleFactor = 1 - Math.abs(position) * 0.1f;
            page.setScaleY(scaleFactor);
            page.setScaleX(scaleFactor);
        });
    }

    private void startAutoScroll() {
        sliderHandler.postDelayed(sliderRunnable, 3000); // Scroll every 3 seconds
    }

    private void stopAutoScroll() {
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = imageSlider.getCurrentItem();
            int nextItem = (currentItem + 1) % images.length; // Loop back to the first image
            imageSlider.setCurrentItem(nextItem, true);
            sliderHandler.postDelayed(this, 3000); // Repeat after 3 seconds
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoScroll(); // Stop scrolling when the activity is not visible
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoScroll(); // Resume scrolling when the activity becomes visible
    }

    private void logFirebaseAnalyticsEvent() {
        Bundle eventBundle = new Bundle();
        eventBundle.putString("user_action", "opened_main_screen");
        firebaseAnalytics.logEvent("main_screen_opened", eventBundle);
    }

    private void showUserProfileBottomSheet() {
        UserProfileBottomSheet bottomSheet = new UserProfileBottomSheet();
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
    }
}
