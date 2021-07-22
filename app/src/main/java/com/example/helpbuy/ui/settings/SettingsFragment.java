package com.example.helpbuy.ui.settings;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.helpbuy.R;
import com.example.helpbuy.ui.transactions.MyRequestFragment;
import com.example.helpbuy.ui.transactions.TransactionFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsFragment extends Fragment {


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button editProfileButton = view.findViewById(R.id.editprofilebutton);
        Button changePasswordButton = view.findViewById(R.id.changepasswordbutton);
        Button sendFeedbackButton = view.findViewById(R.id.sendfeedbackbutton);
        TextView textUsername = view.findViewById(R.id.textView27);
        Button ratingButton = view.findViewById(R.id.ratingbutton);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String username = documentSnapshot.getString("Username");
                        textUsername.setText(username);
                    }
                });


        editProfileButton.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayout fl = view.findViewById(R.id.settingscontainer);
                        fl.removeAllViews();
                        EditProfileFragment editProfileFragment = new EditProfileFragment();
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.settingscontainer, editProfileFragment)
                                .addToBackStack("editprofile")
                                .commit();
                    }
                });

        changePasswordButton.
                setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout fl = view.findViewById(R.id.settingscontainer);
                fl.removeAllViews();
                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.settingscontainer, changePasswordFragment)
                        .addToBackStack("changepassword")
                        .commit();
            }
        });

        sendFeedbackButton.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayout fl = view.findViewById(R.id.settingscontainer);
                        fl.removeAllViews();
                        SendFeedbackFragment sendFeedbackFragment = new SendFeedbackFragment();
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.settingscontainer, sendFeedbackFragment)
                                .addToBackStack("sendfeedback")
                                .commit();
                    }
                });

        ratingButton.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayout fl = view.findViewById(R.id.settingscontainer);
                        fl.removeAllViews();
                        RatingFragment ratingFragment = new RatingFragment();
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.settingscontainer, ratingFragment)
                                .addToBackStack("rating")
                                .commit();
                    }
                });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    SettingsFragment fragment = new SettingsFragment();
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.settingscontainer, fragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });


        return view;
    }

}