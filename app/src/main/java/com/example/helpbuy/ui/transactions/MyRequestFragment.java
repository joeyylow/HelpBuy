package com.example.helpbuy.ui.transactions;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpbuy.R;
import com.example.helpbuy.ui.listrequest.RequestDetailsFragment;
import com.example.helpbuy.ui.listrequest.Requests;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MyRequestFragment extends Fragment {
    private RecyclerView requestsList;
    private FirebaseFirestore db;
    private Button requestViewDetailsButton;
    private FirestoreRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myrequestfull, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        requestsList = view.findViewById(R.id.requests_list);
        String currentUID =  FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query query = db.collection("Job_requests").whereEqualTo("UID", currentUID);

        FirestoreRecyclerOptions<Requests> options = new FirestoreRecyclerOptions.Builder<Requests>()
                .setQuery(query, Requests.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Requests, RequestsViewHolder>(options) {

            @NonNull
            @Override
            public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_myrequestsingle, parent, false);
                return new RequestsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RequestsViewHolder holder, int position, @NonNull Requests model) {
                holder.requestdetails_item.setText(model.getItem());
                holder.requestdetails_location.setText(model.getLocation());
                holder.requestdetails_deliverydate.setText(model.getDeliveryDate());
                holder.requestdetails_deliverytime.setText(model.getDeliveryTime());
                holder.requestdetails_deliveryfees.setText(model.getDeliveryFees());
                holder.requestdetails_quantity.setText(model.getQuantity());
                holder.requestdetails_remarks.setText(model.getRemarks());
            }
        };

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    showFragment();
                    return true;
                }
                return false;
            }
        });

        Context context = view.getContext();
        requestsList.setHasFixedSize(true);
        requestsList.setLayoutManager(new LinearLayoutManager(context));
        requestsList.setAdapter(adapter);
        return view;
    }

    public void showFragment() {
        MyRequestFragment fragment = new MyRequestFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.myrequestfulllist, fragment, "myrequestlist");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onCreate(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public class RequestsViewHolder extends RecyclerView.ViewHolder {
        private TextView requestdetails_item;
        private TextView requestdetails_location;
        private TextView requestdetails_deliverydate;
        private TextView requestdetails_deliverytime;
        private TextView requestdetails_deliveryfees;
        private TextView requestdetails_quantity;
        private TextView requestdetails_remarks;

        public RequestsViewHolder(@NonNull View itemView) {
            super(itemView);
            requestdetails_item = itemView.findViewById(R.id.requestdetails_item);
            requestdetails_location = itemView.findViewById(R.id.requestdetails_location);
            requestdetails_deliverydate = itemView.findViewById(R.id.requestdetails_deliverydate);
            requestdetails_deliverytime = itemView.findViewById(R.id.requestdetails_deliverytime);
            requestdetails_deliveryfees = itemView.findViewById(R.id.requestdetails_deliveryfees);
            requestdetails_quantity = itemView.findViewById(R.id.requestdetails_quantity);
            requestdetails_remarks = itemView.findViewById(R.id.requestdetails_remarks);
//            requestViewDetailsButton = (Button) itemView.findViewById(R.id.listrequest_viewdetailsbtn);
//            requestViewDetailsButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    FragmentTransaction detailsfragment = getFragmentManager().beginTransaction();
//                    detailsfragment.replace(R.id.requestdetails, new RequestDetailsFragment());
//                    detailsfragment.commit();
//                }
//
//            });

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


}
