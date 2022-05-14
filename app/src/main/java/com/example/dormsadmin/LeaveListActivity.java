package com.example.dormsadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dormsadmin.Model.Leave;
import com.example.dormsadmin.databinding.ActivityLeaveListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LeaveListActivity extends AppCompatActivity implements LeaveAdapter.LeaveAction {


    ActivityLeaveListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLeaveListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fetchLeaves();

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchLeaves();
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void fetchLeaves(){

        binding.swipe.setRefreshing(true);
        FirebaseFirestore.getInstance().collection("Leave")
                .whereEqualTo("adminId", FirebaseAuth.getInstance().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        binding.swipe.setRefreshing(false);
                        if(error != null){
                            Toast.makeText(LeaveListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<Leave> leaves = new ArrayList<>();
                        if(value != null)
                            for (DocumentSnapshot doc : value){
                                Leave ob = doc.toObject(Leave.class);
                                leaves.add(ob);
                            }
                        updateRecycler(leaves);
                    }
                });
    }

    LeaveAdapter leaveAdapter;
    void updateRecycler(List<Leave> leaves){

        if(leaveAdapter == null){
            leaveAdapter = new LeaveAdapter(this,this);
            binding.leaveRecycler.setAdapter(leaveAdapter);
            binding.leaveRecycler.setLayoutManager(new LinearLayoutManager(this));
        }

        leaveAdapter.updateList(leaves);
    }

    @Override
    public void onApprove(Leave leave) {

        FirebaseFirestore.getInstance().collection("Leave")
                .document(leave.getLeaveId())
                .update("status","APPROVED")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LeaveListActivity.this, "Leave has been approved", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(LeaveListActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onReject(Leave leave) {
        FirebaseFirestore.getInstance().collection("Leave")
                .document(leave.getLeaveId())
                .update("status","REJECTED")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LeaveListActivity.this, "Leave has been rejected", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(LeaveListActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
