package com.example.dormsadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dormsadmin.Model.Room;
import com.example.dormsadmin.databinding.ActivityAccomodationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AccomodationActivity extends AppCompatActivity implements RoomListAdapter.RoomListAction {


    ActivityAccomodationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAccomodationBinding.inflate(getLayoutInflater());
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
                            Toast.makeText(AccomodationActivity.this, "Failed to fetch", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AccomodationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    RoomListAdapter roomListAdapter;
    void updateRecycler(List<Room> list){

        if(roomListAdapter == null){
            roomListAdapter = new RoomListAdapter(this,this,"ACCOMODATION");
            binding.roomRecycler.setAdapter(roomListAdapter);
            binding.roomRecycler.setLayoutManager(new LinearLayoutManager(this));
        }

        roomListAdapter.updateList(new ArrayList<>(list));
    }

    @Override
    public void onRoomListClick(Room room) {
        AppSingleton.getINSTANCE().setSelectedRoom(room);
        startActivity(new Intent(this,RoomDetailActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchRooms();
    }
}
