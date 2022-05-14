package com.example.dormsadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dormsadmin.Model.Student;
import com.example.dormsadmin.databinding.ActivityStudentDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentDetailActivity extends AppCompatActivity {


    ActivityStudentDetailBinding binding;
    Student student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding  =ActivityStudentDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.studProgress.setVisibility(View.VISIBLE);

        student = AppSingleton.getINSTANCE().getSelectedStudent();
        if(student == null) {
            String studId = getIntent().getStringExtra("STUDENT_ID");
            if(studId == null || studId.length() == 0)
                finish();
            else
                fetchStudent(studId);
        } else updateUi();


        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteStudent();
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void updateUi(){


        binding.studProgress.setVisibility(View.GONE);
        binding.topParent.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(student.getProfileUrl())
                .centerCrop()
                .into(binding.profileImage);

        binding.userName.setText(student.getFull_name());
        binding.college.setText(student.getCollege());
        binding.phone.setText(student.getPhone());
        binding.email.setText(student.getEmail());
        binding.address.setText(student.getAddress());
        binding.password.setText(student.getPassword());
        binding.parentName.setText(student.getGname());
        binding.parentPhone.setText(student.getGphonenumber());
    }


    void fetchStudent(String studId){

        FirebaseFirestore.getInstance()
                .collection("Student")
                .document(studId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            Student doc = task.getResult().toObject(Student.class);
                            if(doc != null){
                                student = doc;
                                updateUi();
                            } else finish();
                        } else {
                            Toast.makeText(StudentDetailActivity.this, "Failed to get student!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

    }
    void deleteStudent(){
        Toast.makeText(this, "Deleting student data", Toast.LENGTH_SHORT).show();
        FirebaseFirestore.getInstance()
                .collection("Student")
                .document(student.getStudId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(StudentDetailActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }
}
