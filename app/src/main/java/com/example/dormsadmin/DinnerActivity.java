package com.example.dormsadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dormsadmin.databinding.ActivityDinnerBinding;

public class DinnerActivity extends AppCompatActivity {
    ActivityDinnerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.addDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new  Intent(DinnerActivity.this,AddMenuActivity.class);
                i.putExtra("TYPE","Dinner");
                startActivity(i);
            }
        });

        binding.viewDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new  Intent(DinnerActivity.this, MenuListActivity.class);
                i.putExtra("TYPE","Dinner");
                startActivity(i);
            }
        });

        binding.selectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new  Intent(DinnerActivity.this, MenuSelectionActivity.class);
                i.putExtra("TYPE","Dinner");
                startActivity(i);
            }
        });

    }
}