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

    private List<String> usersList;

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
        db.collection("Chats").document().get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        db.collection("Chats").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                        usersList.clear();
                                        for (QueryDocumentSnapshot doc : task.getResult()) {

                                            String sender = doc.getString("sender");
                                            String receiver = doc.getString("receiver");
                                            Chat chat = doc.toObject(Chat.class);
                                            chat.setSender(sender);
                                            chat.setReceiver(receiver);
                                            System.out.println(sender);
                                            System.out.println(receiver);

                                            if (chat.getSender().equals(currUserUID)) {
                                                usersList.add(chat.getReceiver());
                                            }
                                            if (chat.getReceiver().equals(currUserUID)) {
                                                usersList.add(chat.getSender());
                                            }
                                        }
                                        readChats();
                                    }
                                });
                    }
                });

        /*reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);

                    if (chat.getSender().equals(fuser.getUid())) {
                        usersList.add(chat.getReceiver());
                    }
                    if (chat.getReceiver().equals(fuser.getUid())) {
                        usersList.add(chat.getSender());
                    }
                }
                readChats();
            }
        });*/
        return view;
    }

    private void readChats() {
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

                                            //display 1 user from chats
                                            for (String id : usersList) {
                                                if (user.getId().equals(id)) {
                                                    if (mUsers.size() != 0) {
                                                        for (User user1 : mUsers) {
                                                            if (!user.getId().equals(user1.getId())) {
                                                                mUsers.add(user);
                                                            }
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

        /*reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    //display 1 user from chats
                    for (String id : usersList) {
                        if (user.getId().equals(id)) {
                            if (mUsers.size() != 0) {
                                for (User user1 : mUsers) {
                                    if (!user.getId().equals(user1.getId())) {
                                        mUsers.add(user);
                                    }
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
        });*/
    }
}