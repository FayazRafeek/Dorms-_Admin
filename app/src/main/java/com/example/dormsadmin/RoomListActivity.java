package com.example.dormsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dormsadmin.Model.Room;
import com.example.dormsadmin.databinding.ActivityViewRoomdetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RoomListActivity extends AppCompatActivity {

    ActivityViewRoomdetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityViewRoomdetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fetchRooms();

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchRooms();
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    void fetchRooms(){

        binding.swipe.setRefreshing(true);
        FirebaseFirestore.getInstance()
                .collection("Admin")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Rooms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        binding.swipe.setRefreshing(false);
                        if(task.isSuccessful()){
                            List<Room> data = new ArrayList<>();
                            for (DocumentSnapshot doc : task.getResult()){
                                Room item = doc.toObject(Room.class);
                                data.add(item);
                            }

                            updateRecycler(data);
                        } else {
                            Toast.makeText(RoomListActivity.this, "Failed to get room list", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    RoomListAdapter roomListAdapter;
        void updateRecycler(List<Room> list){

            if(roomListAdapter == null){
                roomListAdapter = new RoomListAdapter(this);
                binding.recyclerview.setAdapter(roomListAdapter);
                binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
            }

            roomListAdapter.updateList(new ArrayList<>(list));
    }

}