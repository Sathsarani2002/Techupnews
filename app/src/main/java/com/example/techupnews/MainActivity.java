package com.example.techupnews;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Optional: show logged-in username from Intent
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            setTitle("Welcome, " + username);
        }
    }
}
