package com.example.helpbuy.ui.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpbuy.R;
import com.example.helpbuy.ui.Adapter.MessageAdapter;
import com.example.helpbuy.ui.Model.Chat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profileImage;
    TextView username;

    DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Chat> mChat;

    RecyclerView recyclerView;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profileImage = findViewById(R.id.myProfileImage);
        username = findViewById(R.id.myUsername);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        intent = getIntent();
        String userid = intent.getStringExtra("userid");
        String currUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                String currentTime = simpleDateFormat.format(calendar.getTime());

                if (!msg.equals("")) {
                    sendMessage(currUserUID, userid, msg, currentTime);
                    readMessages(currUserUID, userid/*, user.getImageURL()*/);
                } else {
                    Toast.makeText(MessageActivity.this, "You can't sent an empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(userid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String collectedUsername = documentSnapshot.getString("Username");
                        username.setText(collectedUsername);
                        /*User user = documentSnapshot.toObject(User.class);
                        if (user.getImageURL().equals("default")) {
                            myProfileImage.setImageResource(R.mipmap.ic_launcher);
                        } else {
                            Glide.with(ChatMainActivity.this).load(user.getImageURL()).into(myProfileImage);
                        }*/
                        profileImage.setImageResource(R.mipmap.ic_launcher);

                        readMessages(currUserUID, userid/*, user.getImageURL()*/);
                    }
                });

        /*fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")){
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                } else {
                    //and this
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                }

            }*/
    }
    private void sendMessage(String sender, String receiver, String message, String time) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, String> newMessage = new HashMap<>();
        newMessage.put("sender", sender);
        newMessage.put("receiver", receiver);
        newMessage.put("message", message);
        newMessage.put("aTime", time);

        db.collection("Chats").document().set(newMessage);
    }

    private void readMessages(String myID, String userid/*, String imageurl*/) {
        mChat = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Chats").document().get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        db.collection("Chats").orderBy("aTime").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                        mChat.clear();
                                        for (QueryDocumentSnapshot doc : task.getResult()) {

                                            String sender = doc.getString("sender");
                                            String receiver = doc.getString("receiver");
                                            Chat chat = doc.toObject(Chat.class);
                                            chat.setSender(sender);
                                            chat.setReceiver(receiver);

                                            if (chat.getReceiver().equals(myID) && chat.getSender().equals(userid) ||
                                            chat.getReceiver().equals(userid) && chat.getSender().equals(myID)) {
                                                mChat.add(chat);
                                            }
                                            //messageAdapter = new MessageAdapter(MessageActivity.this, mChat, imageurl);
                                            messageAdapter = new MessageAdapter(MessageActivity.this, mChat);
                                            recyclerView.setAdapter(messageAdapter);
                                        }
                                    }
                                });
                    }
                });

        /*reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myID) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myID)) {
                        mChat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(MessageActivity.this, mChat*//*, imageurl*//*);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/
    }
}