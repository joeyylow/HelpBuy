package com.example.helpbuy.ui.listoffer;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpbuy.R;
import com.example.helpbuy.ui.listrequest.ListRequestFragAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListOfferFragAdapter extends RecyclerView.Adapter<ListOfferFragAdapter.ViewHolder> {
    private Context mContext;
    private List<Offers> mOffers;

    public ListOfferFragAdapter(Context mContext, List<Offers> mOffers) {
        this.mOffers = mOffers;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_listoffer, parent, false);
        return new ListOfferFragAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Offers offer = mOffers.get(position);
        holder.location.setText(offer.getLocation());
        holder.dateOfPurchase.setText(offer.getDateOfPurchase());
        holder.duration.setText(offer.getDuration());
        holder.minFeesRequest.setText(offer.getMinFeesRequest());

        holder.viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Job_offers").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    String finalDocID = doc.getString("DOCID");
                                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                    if (offer.getdocID().equals(finalDocID)) {
                                        OfferDetailsFragment frag = new OfferDetailsFragment(finalDocID,
                                                holder.location.getText().toString(),
                                                holder.dateOfPurchase.getText().toString(),
                                                holder.duration.getText().toString(),
                                                holder.minFeesRequest.getText().toString(),
                                                offer.getRemarks(), offer.getUID());
                                        activity.getSupportFragmentManager()
                                                .beginTransaction()
                                                .add(R.id.offerlistfull, frag)
                                                .addToBackStack("offerlist")
                                                .commit();
                                        break;
                                    }
                                }
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOffers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView location;
        public TextView dateOfPurchase;
        public TextView duration;
        public TextView minFeesRequest;

        public TextView remarks;

        public Button viewDetailsBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.offerlist_location);
            dateOfPurchase = itemView.findViewById(R.id.offerlist_dateofpurchase);
            duration = itemView.findViewById(R.id.offerlist_duration);
            minFeesRequest = itemView.findViewById(R.id.offerlist_minfeesrequest);

            remarks = itemView.findViewById(R.id.offerdetails_remarks);

            viewDetailsBtn = itemView.findViewById(R.id.listoffer_viewdetailsbtn);
        }
    }
}
