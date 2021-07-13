package com.example.helpbuy.ui.listrequest;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpbuy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListRequestFragment extends Fragment {
    private RecyclerView requestsList;
    private FirebaseFirestore db;
    private Button requestViewDetailsButton;
    private FirestoreRecyclerAdapter adapter;

    EditText search_location_request;
    private List<Requests> mRequests;
    private ListRequestFragAdapter requestFragAdapter;

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

                holder.listrequest_viewdetailsbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                        String documentID = snapshot.getId();
                        RequestDetailsFragment detailsFragment = new RequestDetailsFragment(documentID,model.getItem(),
                                model.getLocation(),model.getDeliveryDate(),model.getDeliveryTime(),model.getDeliveryFees(),
                                model.getQuantity(), model.getRemarks(),model.getUID());
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

        mRequests = new ArrayList<>();

        //TO SEARCH LOCATION
        search_location_request = view.findViewById(R.id.search_location_request);
        search_location_request.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchLocationRequest(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void searchLocationRequest(String s) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Job_requests").orderBy("LowercapsLocation")
                .startAt(s)
                .endAt(s + "\uf8ff")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        mRequests.clear();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Requests request = doc.toObject(Requests.class);

                            String docID = doc.getString("DOCID");
                            request.setdocID(docID);

                            String personAcceptedUID = request.getaUID();
                            if (personAcceptedUID.equals("")) {
                                mRequests.add(request);
                            }
                        }
                        requestFragAdapter = new ListRequestFragAdapter(getContext(), mRequests);
                        requestsList.setAdapter(requestFragAdapter);
                    }
                });
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
        private Button listrequest_viewdetailsbtn;

        public RequestsViewHolder(@NonNull View itemView){
            super(itemView);
            list_item = itemView.findViewById(R.id.list_item);
            list_location = itemView.findViewById(R.id.list_location);
            list_deliverydate = itemView.findViewById(R.id.list_deliverydate);
            list_deliverytime = itemView.findViewById(R.id.list_deliverytime);
            list_deliveryfees = itemView.findViewById(R.id.list_deliveryfees);
            listrequest_viewdetailsbtn = itemView.findViewById(R.id.listrequest_viewdetailsbtn);
            requestViewDetailsButton = (Button) itemView.findViewById(R.id.listrequest_viewdetailsbtn);
            requestViewDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction detailsfragment = getFragmentManager().beginTransaction();
                    detailsfragment.replace(R.id.requestdetails, new RequestDetailsFragment());
                    detailsfragment.commit();
                }

            });

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
