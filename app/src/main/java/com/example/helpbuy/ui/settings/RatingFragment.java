package com.example.helpbuy.ui.settings;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpbuy.R;
import com.example.helpbuy.ui.Adapter.UserAdapter;
import com.example.helpbuy.ui.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RatingFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserRatingAdapter userRatingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<FirebaseUser> mUsers = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("Users").orderBy("Search").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        //loop through all users
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String docUsername = doc.getString("Username");
                            String docID = doc.getId();
                            String userUID = docID;

                            FirebaseUser user = new FirebaseUser(docUsername,
                                    doc.get("Search").toString(),
                                    doc.get("PhoneNumber").toString(),docID);


                            if (user.getUsername().equals("") || user.getId().equals(currUserUID)) {
                                continue;

                            } else {
                                //CASE 1
                                db.collection("Job_requests")
                                        .whereEqualTo("aUID",currUserUID)
                                        .whereEqualTo("UID",userUID)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                if(task.isSuccessful()&& !task.getResult().isEmpty()) {
                                                    mUsers.add(user);
                                                } else {
                                                    //CASE 2
//                                                    System.out.println("Case 1:"+user.getUsername()+mUsers);
                                                    db.collection("Job_requests")
                                                            .whereEqualTo("UID", currUserUID)
                                                            .whereEqualTo("aUID", userUID)
                                                            .get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                                                    if(task.isSuccessful()&& !task.getResult().isEmpty()) {
                                                                        mUsers.add(user);
                                                                    } else {
                                                                        //CASE 3
//                                                                        System.out.println("Case 2 mUsers:"+user.getUsername()+mUsers);
                                                                        db.collection("Job_offers")
                                                                                .whereEqualTo("UID",currUserUID)
                                                                                .whereEqualTo("aUID",userUID)
                                                                                .get()
                                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                                                                        if(task.isSuccessful() && !task.getResult().isEmpty()) {
                                                                                            mUsers.add(user);
                                                                                        } else {
                                                                                            //CASE 4
//                                                                                            System.out.println("Case 3 mUsers:"+user.getUsername()+mUsers);
                                                                                            db.collection("Job_offers")
                                                                                                    .whereEqualTo("aUID",currUserUID)
                                                                                                    .whereEqualTo("UID",userUID)
                                                                                                    .get()
                                                                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                                                                                            if(task.isSuccessful() && !task.getResult().isEmpty()){
                                                                                                                mUsers.add(user);
                                                                                                            }
//                                                                                                            System.out.println("Final mUsers: "+ mUsers);
                                                                                                            userRatingAdapter = new UserRatingAdapter(getContext(), mUsers);
                                                                                                            recyclerView.setAdapter(userRatingAdapter);
                                                                                                        }
                                                                                                    });
                                                                                        }
                                                                                    }
                                                                                });
                                                                    }
                                                                }
                                                            });
                                                }
//                                                System.out.println("Final mUsers: "+ mUsers);
//                                                userRatingAdapter = new UserRatingAdapter(getContext(), mUsers);
//                                                recyclerView.setAdapter(userRatingAdapter);

                                            }
                                        });

                            }
                        }
                    }
                });

        return view;
    }
}