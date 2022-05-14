package com.example.dormsadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dormsadmin.Model.FeeReceipt;
import com.example.dormsadmin.Model.Student;
import com.example.dormsadmin.databinding.ActivityFeesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FeesActivity extends AppCompatActivity {

    ActivityFeesBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFeesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        fetchPayments();

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPayments();
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    void fetchPayments(){
        binding.swipe.setRefreshing(true);

        FirebaseFirestore.getInstance().collection("Admin")
                .document(FirebaseAuth.getInstance().getUid()).collection("Fees")
                .orderBy("ts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        binding.swipe.setRefreshing(false);

                        if(task.isSuccessful()){

                            List<FeeReceipt> receipts = new ArrayList<>();
                            for(DocumentSnapshot d : task.getResult())
                                receipts.add(d.toObject(FeeReceipt.class));

                            updateRecycler(receipts);

                        } else {
                            Toast.makeText(FeesActivity.this, "Failed to fees data", Toast.LENGTH_SHORT).show();
                            Toast.makeText(FeesActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    FeesAdapter feesAdapter;
    void updateRecycler(List<FeeReceipt> list){

        if(feesAdapter == null){
            feesAdapter = new FeesAdapter(this);
            binding.recycler.setAdapter(feesAdapter);
            binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        }

        feesAdapter.updateList(list);
    }

}
