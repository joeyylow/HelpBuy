package com.example.helpbuy.ui.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.helpbuy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ChangePasswordFragment extends Fragment {

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changepassword, container, false);

        EditText currentpassword = view.findViewById(R.id.currentPassword);
        EditText newpassword = view.findViewById(R.id.newpassword);
        EditText newpasswordagain = view.findViewById(R.id.newpasswordagain);
        Button changebutton = view.findViewById(R.id.changebutton);

        changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), currentpassword.getText().toString().trim());
                if (currentpassword.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (newpassword.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter new password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (newpasswordagain.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please re-enter new password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (currentpassword.getText().toString().equals(newpassword.getText().toString())) {
                    Toast.makeText(getContext(), "Cannot change to old password. Please enter a new password", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((newpassword.getText().toString()).equals(newpasswordagain.getText().toString())){
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(newpassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    currentpassword.getText().clear();
                                                    newpassword.getText().clear();
                                                    newpasswordagain.getText().clear();
                                                    Toast.makeText(getContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getContext(), "Authentication Error. Please re-enter current password", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "New passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        return view;
    }
}