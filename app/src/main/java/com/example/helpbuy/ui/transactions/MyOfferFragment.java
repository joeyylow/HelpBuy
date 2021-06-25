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
import com.example.helpbuy.ui.listoffer.Offers;
import com.example.helpbuy.ui.listrequest.Requests;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MyOfferFragment extends Fragment {
    private RecyclerView offersList;
    private FirebaseFirestore db;
    private Button requestViewDetailsButton;
    private FirestoreRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myofferfull, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        offersList = view.findViewById(R.id.offers_list);
        String currentUID =  FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query query = db.collection("Job_offers").whereEqualTo("UID", currentUID);

        FirestoreRecyclerOptions<Offers> options = new FirestoreRecyclerOptions.Builder<Offers>()
                .setQuery(query, Offers.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Offers, OffersViewHolder>(options) {

            @NonNull
            @Override
            public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_myoffersingle, parent, false);
                return new OffersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OffersViewHolder holder, int position, @NonNull Offers model) {
                holder.offerdetails_location.setText(model.getLocation());
                holder.offerdetails_deliverydate.setText(model.getDateOfPurchase());
                holder.offerdetails_deliverytime.setText(model.getDuration());
                holder.offerdetails_deliveryfees.setText(model.getMinFeesRequest());
                holder.offerdetails_remarks.setText(model.getRemarks());

                DocumentReference docRef = db.collection("Users").document(model.getUID());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            String delivereruser = (String) document.get("Username");
                            holder.offerdetails_delivererusername.setText(delivereruser);
                        }
                    }
                });
                if (model.getaUID().equals("")) {
                    holder.offerdetails_buyerusername.setText("");
                } else {
                    DocumentReference doc = db.collection("Users").document(model.getaUID());
                    doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                String buyeruser = (String) document.get("Username");
                                holder.offerdetails_buyerusername.setText(buyeruser);
                            }
                        }
                    });
                }
            }
        };

//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//                    showFragment();
//                    return true;
//                }
//                return false;
//            }
//        });

        Context context = view.getContext();
        offersList.setHasFixedSize(true);
        offersList.setLayoutManager(new LinearLayoutManager(context));
        offersList.setAdapter(adapter);
        return view;
    }

//    public void showFragment() {
//        MyRequestFragment fragment = new MyRequestFragment();
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.myofferfulllist, fragment, "myofferlist");
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

    public void onCreate(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public class OffersViewHolder extends RecyclerView.ViewHolder {
        private TextView offerdetails_location;
        private TextView offerdetails_deliverydate;
        private TextView offerdetails_deliverytime;
        private TextView offerdetails_deliveryfees;
        private TextView offerdetails_remarks;
        private TextView offerdetails_delivererusername;
        private TextView offerdetails_buyerusername;

        public OffersViewHolder(@NonNull View itemView) {
            super(itemView);
            offerdetails_location = itemView.findViewById(R.id.offerdetails_location);
            offerdetails_deliverydate = itemView.findViewById(R.id.offerdetails_deliverydate);
            offerdetails_deliverytime = itemView.findViewById(R.id.offerdetails_deliverytime);
            offerdetails_deliveryfees = itemView.findViewById(R.id.offerdetails_deliveryfees);
            offerdetails_remarks = itemView.findViewById(R.id.offerdetails_remarks);
            offerdetails_delivererusername = itemView.findViewById(R.id.delivererusername);
            offerdetails_buyerusername = itemView.findViewById(R.id.buyerusername);
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
