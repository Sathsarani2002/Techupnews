package com.example.techupnews; // Replace with your actual package name

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast; // Used for simple feedback on menu item clicks

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView menuIcon; // The icon in the header of activity_main.xml to open the drawer
    private ImageView bottomNavMenuIcon; // The menu icon in the bottom navigation (optional trigger)
    private ImageView closeIcon; // The 'X' icon within your user profile layout (userscreen.xml)

    // References to your menu item LinearLayouts within the drawer (userscreen.xml)
    private LinearLayout menuItemHome;
    private LinearLayout menuItemSportNews;
    private LinearLayout menuItemAcademicNews;
    private LinearLayout menuItemFacultyEventsNews;
    private LinearLayout menuItemDevInfo;
    private LinearLayout menuItemLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the content view to your main layout with DrawerLayout

        // 1. Initialize DrawerLayout and main activity UI elements
        drawerLayout = findViewById(R.id.drawer_layout);
        menuIcon = findViewById(R.id.menuIcon); // Get the menu icon from the header of activity_main.xml
        bottomNavMenuIcon = findViewById(R.id.bottomNavIconMenu); // Get the menu icon from the bottom navigation

        // 2. Get reference to the root of the included profile screen layout (userscreen.xml)
        // This is crucial because closeIcon and menu items are inside this included layout.
        View profileScreenRootView = findViewById(R.id.userProfileScrollView);

        // 3. Initialize UI elements within the profile drawer (if profileScreenRootView is found)
        if (profileScreenRootView != null) {
            closeIcon = profileScreenRootView.findViewById(R.id.closeIcon);

            // Initialize all menu item LinearLayouts by finding them within the profileScreenRootView
            menuItemHome = profileScreenRootView.findViewById(R.id.menuItemHome);
            menuItemSportNews = profileScreenRootView.findViewById(R.id.menuItemSportNews);
            menuItemAcademicNews = profileScreenRootView.findViewById(R.id.menuItemAcademicNews);
            menuItemFacultyEventsNews = profileScreenRootView.findViewById(R.id.menuItemFacultyEventsNews);
            menuItemDevInfo = profileScreenRootView.findViewById(R.id.menuItemDevInfo);
            menuItemLogOut = profileScreenRootView.findViewById(R.id.menuItemLogOut);
        }


        // 4. Set Click Listeners for opening and closing the drawer

        // Click listener for the header menu icon to open the drawer
        if (menuIcon != null) {
            menuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (drawerLayout != null && profileScreenRootView != null) {
                        drawerLayout.openDrawer(profileScreenRootView); // Open the drawer
                    }
                }
            });
        }

        // Click listener for the bottom navigation menu icon to open the drawer (if desired)
        if (bottomNavMenuIcon != null) {
            bottomNavMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (drawerLayout != null && profileScreenRootView != null) {
                        drawerLayout.openDrawer(profileScreenRootView); // Open the drawer
                    }
                }
            });
        }

        // Click listener for the close icon inside the drawer to close it
        if (closeIcon != null) {
            closeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (drawerLayout != null && profileScreenRootView != null) {
                        drawerLayout.closeDrawer(profileScreenRootView); // Close the drawer
                    }
                }
            });
        }

        // 5. Set Click Listeners for Menu Items within the Drawer
        // These will typically perform an action and then close the drawer.

        if (menuItemHome != null) {
            menuItemHome.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this, "Home Clicked - Drawer will close", Toast.LENGTH_SHORT).show();
                // Add your navigation logic or action for "Home" here
                if (drawerLayout != null && profileScreenRootView != null) {
                    drawerLayout.closeDrawer(profileScreenRootView); // Close drawer after action
                }
            });
        }

        if (menuItemSportNews != null) {
            menuItemSportNews.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this, "Sport News Clicked - Drawer will close", Toast.LENGTH_SHORT).show();
                // Add your navigation logic or action for "Sport News" here
                if (drawerLayout != null && profileScreenRootView != null) {
                    drawerLayout.closeDrawer(profileScreenRootView);
                }
            });
        }

        if (menuItemAcademicNews != null) {
            menuItemAcademicNews.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this, "Academic News Clicked - Drawer will close", Toast.LENGTH_SHORT).show();
                // Add your navigation logic or action for "Academic News" here
                if (drawerLayout != null && profileScreenRootView != null) {
                    drawerLayout.closeDrawer(profileScreenRootView);
                }
            });
        }

        if (menuItemFacultyEventsNews != null) {
            menuItemFacultyEventsNews.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this, "Faculty Events News Clicked - Drawer will close", Toast.LENGTH_SHORT).show();
                // Add your navigation logic or action for "Faculty Events News" here
                if (drawerLayout != null && profileScreenRootView != null) {
                    drawerLayout.closeDrawer(profileScreenRootView);
                }
            });
        }

        if (menuItemDevInfo != null) {
            menuItemDevInfo.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this, "Dev Info Clicked - Drawer will close", Toast.LENGTH_SHORT).show();
                // Add your navigation logic or action for "Dev Info" here
                if (drawerLayout != null && profileScreenRootView != null) {
                    drawerLayout.closeDrawer(profileScreenRootView);
                }
            });
        }

        if (menuItemLogOut != null) {
            menuItemLogOut.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this, "Log Out Clicked - Drawer will close", Toast.LENGTH_SHORT).show();
                // Add your Log Out logic here (e.g., clear user session, navigate to login screen)
                if (drawerLayout != null && profileScreenRootView != null) {
                    drawerLayout.closeDrawer(profileScreenRootView);
                }
            });
        }
    }

    // You can optionally override onBackPressed to close the drawer first if it's open
    @Override
    public void onBackPressed() {
        View profileScreenRootView = findViewById(R.id.userProfileScrollView);
        if (drawerLayout != null && profileScreenRootView != null && drawerLayout.isDrawerOpen(profileScreenRootView)) {
            drawerLayout.closeDrawer(profileScreenRootView);
        } else {
            super.onBackPressed();
        }
    }
}