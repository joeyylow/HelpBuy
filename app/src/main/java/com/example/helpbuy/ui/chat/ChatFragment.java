package com.example.helpbuy.ui.chat;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.helpbuy.R;
import com.example.helpbuy.ui.Adapter.UserAdapter;
import com.example.helpbuy.ui.Model.Chat;
import com.example.helpbuy.ui.Model.Chatlist;
import com.example.helpbuy.ui.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;

    private List<Chatlist> usersList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String currUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        usersList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Chatlist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                usersList.clear();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Chatlist chatlist = doc.toObject(Chatlist.class);

                    String senderID = doc.getString("sender");
                    String receiverID = doc.getString("receiver");
                    System.out.println(senderID);
                    System.out.println(receiverID);

                    if (senderID.equals(currUserUID)) {
                        String id = doc.getString("receiver");
                        chatlist.setId(id);
                        usersList.add(chatlist);
                    }

                    if (receiverID.equals(currUserUID)) {
                        String id = doc.getString("sender");
                        chatlist.setId(id);
                        usersList.add(chatlist);
                    }
                }
                chatList();
            }
        });
        return view;
    }

    private void chatList() {
        mUsers = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document().get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        db.collection("Users").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                        mUsers.clear();
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            User user = doc.toObject(User.class);
                                            String docID = doc.getId();
                                            user.setId(docID);
                                            String username = doc.getString("Username");
                                            user.setUsername(username);

                                            for (Chatlist chatlist : usersList) {
                                                if (user.getId().equals(chatlist.getId())) {
                                                    if (mUsers.size() != 0) {
                                                        if (!mUsers.contains(user)) {
                                                            mUsers.add(user);
                                                        }
                                                    } else {
                                                        mUsers.add(user);
                                                    }
                                                }
                                            }
                                        }
                                        userAdapter = new UserAdapter(getContext(), mUsers);
                                        recyclerView.setAdapter(userAdapter);
                                    }
                                });
                    }
                });
    }
}