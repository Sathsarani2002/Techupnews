package com.example.techupnews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UserProfileBottomSheet extends BottomSheetDialogFragment {

    private static final int REQUEST_EDIT_PROFILE = 1001;  // Request code for EditProfileActivity

    public interface CategorySelectionListener {
        void onCategorySelected(String category);
    }

    private CategorySelectionListener categorySelectionListener;

    public void setCategorySelectionListener(CategorySelectionListener listener) {
        this.categorySelectionListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog d = (BottomSheetDialog) dialogInterface;
            View bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile_bottom_sheet, container, false);

        Context context = getContext();
        if (context != null) {
            loadUserData(view, context);
        }

        // Category click listeners
        view.findViewById(R.id.menuSports).setOnClickListener(v -> {
            if (categorySelectionListener != null) {
                categorySelectionListener.onCategorySelected("Sports");
            }
            dismiss();
        });

        view.findViewById(R.id.menuAcademic).setOnClickListener(v -> {
            if (categorySelectionListener != null) {
                categorySelectionListener.onCategorySelected("Academic");
            }
            dismiss();
        });

        view.findViewById(R.id.menuEvents).setOnClickListener(v -> {
            if (categorySelectionListener != null) {
                categorySelectionListener.onCategorySelected("Events");
            }
            dismiss();
        });

        view.findViewById(R.id.menuHome).setOnClickListener(v -> {
            if (categorySelectionListener != null) {
                categorySelectionListener.onCategorySelected("Sports"); // Home -> Sports
            }
            dismiss();
        });

        // Close button
        ImageView closeButton = view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> dismiss());

        // Edit Profile opens EditProfileActivity for result
        TextView editProfile = view.findViewById(R.id.editProfile);
        editProfile.setOnClickListener(v -> {
            if (context != null) {
                Intent intent = new Intent(context, EditProfileActivity.class);
                startActivityForResult(intent, REQUEST_EDIT_PROFILE);
                // Do NOT dismiss here so we can refresh data on return
            }
        });

        // Dev Info opens DevInfoActivity
        View menuDevInfo = view.findViewById(R.id.menuDevInfo);
        menuDevInfo.setOnClickListener(v -> {
            if (context != null) {
                Intent intent = new Intent(context, DevInfoActivity.class);
                startActivity(intent);
                dismiss();
            }
        });

        // Logout with confirmation dialog
        View logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            if (context != null) {
                new AlertDialog.Builder(context)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            DatabaseHelper dbHelper = new DatabaseHelper(context);
                            dbHelper.clearLoggedInUser(context);
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            dismiss();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return view;
    }

    private void loadUserData(View view, Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        String loggedInUsername = databaseHelper.getLoggedInUser(context);
        if (loggedInUsername != null) {
            String[] userDetails = databaseHelper.getUserDetails(loggedInUsername);
            if (userDetails != null) {
                TextView usernameTextView = view.findViewById(R.id.username);
                TextView emailTextView = view.findViewById(R.id.email);
                usernameTextView.setText(userDetails[0]);
                emailTextView.setText(userDetails[1]);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        View view = getView();
        Context context = getContext();
        if (view != null && context != null) {
            loadUserData(view, context);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EDIT_PROFILE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            String updatedUsername = data.getStringExtra("username");
            String updatedEmail = data.getStringExtra("email");

            View view = getView();
            if (view != null) {
                TextView usernameTextView = view.findViewById(R.id.username);
                TextView emailTextView = view.findViewById(R.id.email);

                if (updatedUsername != null) {
                    usernameTextView.setText(updatedUsername);
                }
                if (updatedEmail != null) {
                    emailTextView.setText(updatedEmail);
                }
            }
        }
    }
}
