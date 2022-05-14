package com.example.dormsadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dormsadmin.databinding.ActivityBreakfastBinding;

public class BreakfastActivity extends AppCompatActivity {
    Button add_break,view_break;

    ActivityBreakfastBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBreakfastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BreakfastActivity.this, AddMenuActivity.class);
                i.putExtra("TYPE","Breakfast");
                startActivity(i);
            }
        });
        binding.viewDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BreakfastActivity.this, MenuListActivity.class);
                i.putExtra("TYPE","Breakfast");
                startActivity(i);
            }
        });

        binding.selectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BreakfastActivity.this,MenuSelectionActivity.class);
                intent.putExtra("TYPE","Breakfast");
                startActivity(intent);
            }
        });

    }
}