package com.example.techupnews;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView newsTitle;
    ImageView iconMenu, iconSports, iconAcademic, iconEvents, iconHome;
    ViewPager2 imageSlider;
    RecyclerView newsRecycler;

    int[] images = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6
    };

    private Handler sliderHandler;
    private FirebaseAnalytics firebaseAnalytics;

    private NewsDatabaseHelper dbHelper;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        logFirebaseAnalyticsEvent();

        // Initialize Views
        newsTitle = findViewById(R.id.newsTitle);
        iconMenu = findViewById(R.id.iconMenu);
        iconSports = findViewById(R.id.iconSports);
        iconAcademic = findViewById(R.id.iconAcademic);
        iconEvents = findViewById(R.id.iconEvents);
        iconHome = findViewById(R.id.iconHome);
        imageSlider = findViewById(R.id.imageSlider);
        newsRecycler = findViewById(R.id.newsRecycler);

        // Image Slider Setup
        imageSlider.setAdapter(new CarouselAdapter(this, images));
        configureViewPager();

        // Auto-scroll
        sliderHandler = new Handler(Looper.getMainLooper());
        startAutoScroll();

        // SQLite & RecyclerView
        dbHelper = new NewsDatabaseHelper(this);
        newsRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(this, new ArrayList<>());
        newsRecycler.setAdapter(adapter);

        // Load Default Category
        loadNews("Sports");

        // Button Clicks
        iconMenu.setOnClickListener(v -> showUserProfileBottomSheet());

        iconSports.setOnClickListener(v -> loadNews("Sports"));
        iconAcademic.setOnClickListener(v -> loadNews("Academic"));
        iconEvents.setOnClickListener(v -> loadNews("Events"));
        iconHome.setOnClickListener(v -> loadNews("Sports"));
    }

    private void loadNews(String category) {
        newsTitle.setText(category);
        ArrayList<News> newsList = dbHelper.getNewsByCategory(category);
        adapter.updateList(newsList);
    }

    private void configureViewPager() {
        imageSlider.setClipToPadding(false);
        imageSlider.setClipChildren(false);
        imageSlider.setOffscreenPageLimit(3);
        imageSlider.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        int offset = 50;
        float density = getResources().getDisplayMetrics().density;

        imageSlider.setPageTransformer((page, position) -> {
            float translationX = -(offset * density) * position;
            if (imageSlider.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                page.setTranslationX(translationX);
            }
            float scaleFactor = 1 - Math.abs(position) * 0.1f;
            page.setScaleY(scaleFactor);
            page.setScaleX(scaleFactor);
        });
    }

    private void startAutoScroll() {
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    private void stopAutoScroll() {
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = imageSlider.getCurrentItem();
            int nextItem = (currentItem + 1) % images.length;
            imageSlider.setCurrentItem(nextItem, true);
            sliderHandler.postDelayed(this, 3000);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoScroll();
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
