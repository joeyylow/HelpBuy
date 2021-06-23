package com.example.helpbuy.ui.listrequest;

import android.content.Context;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpbuy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.HashMap;
import java.util.Map;


public class ListRequestFragment extends Fragment {
    private RecyclerView requestsList;
    private FirebaseFirestore db;
    private Button requestViewDetailsButton;
    private FirestoreRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listrequest_list, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        requestsList = view.findViewById(R.id.requests_list);
        Query query = db.collection("Job_requests").whereEqualTo("aUID","");
        FirestoreRecyclerOptions<Requests> options = new FirestoreRecyclerOptions.Builder<Requests>()
                .setQuery(query,Requests.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Requests,RequestsViewHolder>(options) {

            @NonNull
            @Override
            public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_listrequest,parent,false);
                return new RequestsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RequestsViewHolder holder, int position, @NonNull Requests model) {
                holder.list_item.setText(model.getItem());
                holder.list_location.setText(model.getLocation());
                holder.list_deliverydate.setText(model.getDeliveryDate());
                holder.list_deliverytime.setText(model.getDeliveryTime());
                holder.list_deliveryfees.setText(model.getDeliveryFees());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                        String documentID = snapshot.getId();
                        RequestDetailsFragment detailsFragment = new RequestDetailsFragment(documentID,model.getItem(),
                                model.getLocation(),model.getDeliveryDate(),model.getDeliveryTime(),model.getDeliveryFees(),
                                model.getQuantity(), model.getRemarks());
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.requestlistfull,detailsFragment)
                                .addToBackStack("requestlist")
                                .commit();
                    }
                });
            }
        };

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
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
        ListRequestFragment fragment = new ListRequestFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.requestlistfull, fragment,"requestlist");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onCreate(View view, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    public class RequestsViewHolder extends RecyclerView.ViewHolder{
        private TextView list_item;
        private TextView list_location;
        private TextView list_deliverydate;
        private TextView list_deliverytime;
        private TextView list_deliveryfees;

        public RequestsViewHolder(@NonNull View itemView){
            super(itemView);
            list_item = itemView.findViewById(R.id.list_item);
            list_location = itemView.findViewById(R.id.list_location);
            list_deliverydate = itemView.findViewById(R.id.list_deliverydate);
            list_deliverytime = itemView.findViewById(R.id.list_deliverytime);
            list_deliveryfees = itemView.findViewById(R.id.list_deliveryfees);
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

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_listrequest_list, container, false);
//
//        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(new MylistrequestRecyclerViewAdapter(PlaceholderContent.ITEMS));
//        }
//        return view;
//    }
}