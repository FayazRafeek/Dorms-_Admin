package com.example.dormsadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dormsadmin.databinding.ActivityViewRoomBinding;

public class RoomHomeActivity extends AppCompatActivity {

    ActivityViewRoomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.newRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RoomHomeActivity.this, AddRoomActivity.class);
                startActivity(i);
            }
        });
        binding.viewRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoomHomeActivity.this, RoomListActivity.class));
            }
        });
    }
}