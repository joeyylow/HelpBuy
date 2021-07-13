package com.example.helpbuy.ui.listrequest;

import android.app.FragmentTransaction;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpbuy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListRequestFragAdapter extends RecyclerView.Adapter<ListRequestFragAdapter.ViewHolder> {
    private Context mContext;
    private List<Requests> mRequests;

    public ListRequestFragAdapter(Context mContext, List<Requests> mRequests) {
        this.mRequests = mRequests;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_listrequest, parent, false);
        return new ListRequestFragAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Requests request = mRequests.get(position);
        holder.list_item.setText(request.getItem());
        holder.list_location.setText(request.getLocation());
        holder.list_deliverydate.setText(request.getDeliveryDate());
        holder.list_deliverytime.setText(request.getDeliveryTime());
        holder.list_deliveryfees.setText(request.getDeliveryFees());

        holder.listrequest_viewdetailsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Job_requests").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    String finalDocID = doc.getString("DOCID");
                                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                    if (request.getdocID().equals(finalDocID)) {
                                        RequestDetailsFragment frag = new RequestDetailsFragment(doc.getId(),
                                                holder.list_item.getText().toString(),
                                                holder.list_location.getText().toString(),
                                                holder.list_deliverydate.getText().toString(),
                                                holder.list_deliverytime.getText().toString(),
                                                holder.list_deliveryfees.getText().toString(),
                                                request.getQuantity(), request.getRemarks(), request.getUID());
                                        activity.getSupportFragmentManager()
                                                .beginTransaction()
                                                .add(R.id.requestlistfull, frag)
                                                .addToBackStack("requestlist")
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
        return mRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView list_item;
        public TextView list_location;
        public TextView list_deliverydate;
        public TextView list_deliverytime;
        public TextView list_deliveryfees;

        public TextView quantity;
        public TextView remarks;

        public Button listrequest_viewdetailsbtn;

        public ViewHolder(View itemView) {
            super(itemView);

            list_item = itemView.findViewById(R.id.list_item);
            list_location = itemView.findViewById(R.id.list_location);
            list_deliverydate = itemView.findViewById(R.id.list_deliverydate);
            list_deliverytime = itemView.findViewById(R.id.list_deliverytime);
            list_deliveryfees = itemView.findViewById(R.id.list_deliveryfees);

            quantity = itemView.findViewById(R.id.requestdetails_quantity);
            remarks = itemView.findViewById(R.id.requestdetails_remarks);

            listrequest_viewdetailsbtn = (Button) itemView.findViewById(R.id.listrequest_viewdetailsbtn);
        }
    }
}
