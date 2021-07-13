package com.example.helpbuy;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpbuy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputUsername, inputEmail, inputPhoneNumber, inputPassword, inputPasswordAgain;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    FirebaseFirestore db;
    private boolean answer;
    //private static final String TAG = "SignUpActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputUsername = (EditText) findViewById(R.id.username);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        inputPassword = (EditText) findViewById(R.id.password);
        inputPasswordAgain = (EditText) findViewById(R.id.reEnterPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

//        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

//        btnResetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
//            }
//        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputUsername.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String passwordAgain = inputPasswordAgain.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(passwordAgain)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match. Re-enter again.", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    db = FirebaseFirestore.getInstance();
                    Query query = db.collection("Users").whereEqualTo("Search", username.toLowerCase());
                    Task<QuerySnapshot> tasksnapshot = query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            QuerySnapshot snapshot = task.getResult();
                            if (!snapshot.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Username Exists. Please enter another username", Toast.LENGTH_SHORT).show();
                            } else {
                                auth = FirebaseAuth.getInstance();
                                progressBar.setVisibility(View.VISIBLE);
                                auth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (!task.isSuccessful()) {
                                                    View contextView2 = (View) findViewById(R.id.sign_up_button);
                                                    Snackbar snackbar2 = Snackbar.make(contextView2, "Registration Failed", Snackbar.LENGTH_LONG);
                                                    snackbar2.setAction("Ok", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            snackbar2.dismiss();
                                                        }
                                                    });
                                                    snackbar2.show();

                                                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                                            Toast.LENGTH_SHORT).show();
                                                } else {
                                                    //userUID = user.getUid();
                                                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                                                    String name = inputUsername.getText().toString();
                                                    String phoneNumber = inputPhoneNumber.getText().toString();

                                                    Map<String, String> newPost = new HashMap<>();
                                                    newPost.put("Username", name);
                                                    newPost.put("PhoneNumber", phoneNumber);
                                                    /*newPost.put("imageURL", "default");
                                                    newPost.put("Status", "offline");*/
                                                    newPost.put("Search", name.toLowerCase());

                                                    db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(newPost);

                                                    //Toast.makeText(SignUpActivity.this, "Registration Successful" , Toast.LENGTH_SHORT).show();
                                                    View contextView = (View) findViewById(R.id.sign_up_button);

                                                    Snackbar snackbar = Snackbar.make(contextView, "Registration Successful. Please log in.", Snackbar.LENGTH_LONG);
                                                    //.setAction("OK") {finish();}
                                                    snackbar.setAction("Ok", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            // Call your action method here
                                                            snackbar.dismiss();
                                                        }
                                                    });
                                                    snackbar.show();

                                                    progressBar.setVisibility(View.GONE);
                                                }
                                                // If sign in fails, display a message to the user. If sign in succeeds
                                                // the auth state listener will be notified and logic to handle the
                                                // signed in user can be handled in the listener.


                                            }
                                        });
                            }
                        }
                    });
                }


                /*// Send user a verification email EDIT MORE IN PHASE 3
                user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Verification email has been sent.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
*/
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}