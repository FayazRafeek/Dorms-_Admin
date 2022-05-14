package com.example.dormsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dormsadmin.Model.Complaint;
import com.example.dormsadmin.databinding.ActivityQueriesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ComplaintsActivity extends AppCompatActivity implements ComplaintAdapter.ComplaintAction {

    ActivityQueriesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityQueriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fetchComplaints();

        binding.compSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchComplaints();
            }
        });
    }


    void fetchComplaints(){

        binding.compSwipe.setRefreshing(true);

        FirebaseFirestore.getInstance()
                .collection("Complaints")
                .whereEqualTo("adminId", FirebaseAuth.getInstance().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                binding.compSwipe.setRefreshing(false);
                if(task.isSuccessful()){

                    List<Complaint> complaints = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult()){
                        Complaint item = doc.toObject(Complaint.class);
                        complaints.add(item);
                    }

                    updateRecycler(complaints);
                 } else
                    Toast.makeText(ComplaintsActivity.this, "Failed to get complaints", Toast.LENGTH_SHORT).show();
            }
        });

    }

    ComplaintAdapter complaintAdapter;
    void updateRecycler(List<Complaint> complaints){

        if(complaintAdapter == null){

            complaintAdapter = new ComplaintAdapter(this);
            binding.compRecycler.setAdapter(complaintAdapter);
            binding.compRecycler.setLayoutManager(new LinearLayoutManager(this));
        }

        complaintAdapter.updateList(complaints);
    }

    @Override
    public void onCompClick(Complaint complaint) {
        AppSingleton.getINSTANCE().setSelectedComplaint(complaint);
        startActivity(new Intent(this,ComplaintDetailActivity.class));
    }
}