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

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UserProfileBottomSheet extends BottomSheetDialogFragment {

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
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            String loggedInUsername = dbHelper.getLoggedInUser(context);
            if (loggedInUsername != null) {
                String[] userDetails = dbHelper.getUserDetails(loggedInUsername);
                if (userDetails != null) {
                    TextView usernameTextView = view.findViewById(R.id.username);
                    TextView emailTextView = view.findViewById(R.id.email);

                    usernameTextView.setText(userDetails[0]);
                    emailTextView.setText(userDetails[1]);
                }
            }
        }

        // Category click listeners that notify MainActivity via the listener
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

        // Edit Profile opens EditProfileActivity
        TextView editProfile = view.findViewById(R.id.editProfile);
        editProfile.setOnClickListener(v -> {
            if (context != null) {
                Intent intent = new Intent(context, EditProfileActivity.class);
                startActivity(intent);
                dismiss();
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
                            dbHelper.logoutUser(context);

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

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }
}
