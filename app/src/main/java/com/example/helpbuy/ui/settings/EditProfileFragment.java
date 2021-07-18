package com.example.helpbuy.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.helpbuy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import org.jetbrains.annotations.NotNull;

public class EditProfileFragment extends Fragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);

        EditText inputNewUsername = view.findViewById(R.id.username);
        EditText inputNewPhoneNumber = view.findViewById(R.id.phoneNumber);
        Button editButton = view.findViewById(R.id.editbutton);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView email = (TextView) view.findViewById(R.id.textView26);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = user.getEmail();
        email.setText(userEmail);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputNewUsername.getText().toString().trim();
                String phoneNumber = inputNewPhoneNumber.getText().toString().trim();

                String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DocumentReference currentDoc = db.collection("Users").document(currentUID);

                if (username.isEmpty() && phoneNumber.isEmpty()){
                    Toast.makeText(getContext(), "Please enter username or phone number", Toast.LENGTH_SHORT).show();
                }

                if (!phoneNumber.isEmpty() && phoneNumber.length()<8){
                    Toast.makeText(getContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                }

                else if (!username.isEmpty() && phoneNumber.isEmpty()){
                    Query query = db.collection("Users").whereEqualTo("Search", username.toLowerCase());
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            QuerySnapshot snapshot = task.getResult();
                            if (!snapshot.isEmpty()) {
                                Toast.makeText(getContext(), "Username Exists. Please enter another username", Toast.LENGTH_SHORT).show();
                            } else {
                                currentDoc.update("Username", username);
                                currentDoc.update("Search", username.toLowerCase());
                                Toast.makeText(getContext(), "Profile updated.", Toast.LENGTH_SHORT).show();
                                inputNewUsername.getText().clear();
                            }
                        }
                    });

                }

                else if (username.isEmpty() && !phoneNumber.isEmpty()){
                    updatePhoneNumber(currentDoc,phoneNumber);
                    Toast.makeText(getContext(), "Profile updated.", Toast.LENGTH_SHORT).show();
                    inputNewPhoneNumber.getText().clear();
                }

                else {
                    updatePhoneNumber(currentDoc,phoneNumber);
                    updateUsername(currentDoc,username);
                    Toast.makeText(getContext(), "Profile updated.", Toast.LENGTH_SHORT).show();
                    inputNewUsername.getText().clear();
                    inputNewPhoneNumber.getText().clear();
                }
            }
        });
        return view;
    }

    public void updatePhoneNumber(DocumentReference currentDoc, String phoneNumber) {
        currentDoc.update("PhoneNumber", phoneNumber);
    }

    public void updateUsername(DocumentReference currentDoc, String username){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("Users").whereEqualTo("Search", username.toLowerCase());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    Toast.makeText(getContext(), "Username Exists. Please enter another username", Toast.LENGTH_SHORT).show();
                } else {
                    currentDoc.update("Username", username);
                    currentDoc.update("Search", username.toLowerCase());
                }
            }
        });
    }

}
