package com.example.helpbuy.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.helpbuy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendFeedbackFragment extends Fragment {

    public static SendFeedbackFragment newInstance() {
        return new SendFeedbackFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sendfeedback, container, false);
        Button submitbutton = view.findViewById(R.id.button);
        EditText feedback = view.findViewById(R.id.feedback);
        Switch followupswitch = view.findViewById(R.id.switch1);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userFeedback = feedback.getText().toString();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Map<String, String> newFeedback = new HashMap<>();
                newFeedback.put("Feedback", userFeedback);
                newFeedback.put("UID", uid);
                if (followupswitch.isChecked()) {
                    newFeedback.put("followUpNeeded","true");
                } else {
                    newFeedback.put("followUpNeeded","false");
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Feedback").add(newFeedback);
                Toast.makeText(getContext(), "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                feedback.getText().clear();
            }
        });

        return view;
    }
}
