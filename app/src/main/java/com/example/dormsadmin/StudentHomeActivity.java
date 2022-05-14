package com.example.dormsadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dormsadmin.databinding.ActivityStudentDetailsBinding;

public class StudentHomeActivity extends AppCompatActivity {


    ActivityStudentDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStudentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.newStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentHomeActivity.this, AddStudentActivity.class);
                startActivity(i);
            }
        });
        binding.viewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentHomeActivity.this, StudentListActivity.class);
                startActivity(i);
            }
        });

    }
}