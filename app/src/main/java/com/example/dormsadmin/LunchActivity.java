package com.example.dormsadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dormsadmin.databinding.ActivityLunchBinding;

public class LunchActivity extends AppCompatActivity {

    ActivityLunchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLunchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LunchActivity.this,AddMenuActivity.class);
                i.putExtra("TYPE","Lunch");
                startActivity(i);
            }
        });

        binding.viewDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LunchActivity.this, MenuListActivity.class);
                intent.putExtra("TYPE","Lunch");
                startActivity(intent);
            }
        });

        binding.selectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LunchActivity.this, MenuSelectionActivity.class);
                intent.putExtra("TYPE","Lunch");
                startActivity(intent);
            }
        });
    }
}