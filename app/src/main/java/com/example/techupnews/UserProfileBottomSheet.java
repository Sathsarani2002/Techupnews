package com.example.techupnews;

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

        // Close button listener
        ImageView closeButton = view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> dismiss());

        // Edit Profile click listener to open EditProfileActivity
        TextView editProfile = view.findViewById(R.id.editProfile);
        editProfile.setOnClickListener(v -> {
            if (context != null) {
                Intent intent = new Intent(context, EditProfileActivity.class);
                startActivity(intent);
                dismiss();  // close the bottom sheet after launching activity
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
