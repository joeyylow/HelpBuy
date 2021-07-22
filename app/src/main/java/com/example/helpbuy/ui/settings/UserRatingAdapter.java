package com.example.helpbuy.ui.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpbuy.R;
import com.example.helpbuy.ui.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRatingAdapter extends RecyclerView.Adapter<UserRatingAdapter.ViewHolder> {
    private Context mContext;
    private List<FirebaseUser> mUsers;

    public UserRatingAdapter(Context mContext, List<FirebaseUser> mUsers) {
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        FirebaseUser user = mUsers.get(position);
        holder.myUsername.setText(user.getUsername());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(user);
            }

        });
    }

    public void ShowDialog(FirebaseUser user) {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(mContext);

        LinearLayout linearLayout = new LinearLayout(mContext);
        final RatingBar rating = new RatingBar(mContext);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rating.setLayoutParams(lp);
        rating.setNumStars(5);
        rating.setStepSize(1);

        String currUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //add ratingBar to linearLayout
        linearLayout.addView(rating);

        popDialog.setTitle("Rate your experience with "+user.getUsername());

        popDialog.setMessage("Note: You can only rate each user once");

        //add linearLayout to dailog
        popDialog.setView(linearLayout);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                System.out.println("Rated val:"+v);
                Map<String, String> newPost = new HashMap<>();
                newPost.put("ratedUserID", user.getId());
                newPost.put("userID",currUserUID);
                newPost.put("rating",v+"");
                newPost.put("date", String.valueOf(Calendar.getInstance().getTime()));
                String documentID = currUserUID+user.getId();
                db.collection("Rating")
                        .document(documentID)
                        .set(newPost);

            }
        });

        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        textView.setText(String.valueOf(rating.getProgress()));
                        dialog.dismiss();
                    }

                })
                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        popDialog.create();
        popDialog.show();

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView myProfileImage;
        public TextView myUsername;

        public ViewHolder(View itemView) {
            super(itemView);

            myProfileImage = itemView.findViewById(R.id.myProfileImage);
            myUsername = itemView.findViewById(R.id.myUsername);
        }
    }


}
