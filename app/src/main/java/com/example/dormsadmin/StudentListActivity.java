package com.example.dormsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dormsadmin.Model.Student;
import com.example.dormsadmin.databinding.ActivityViewStudentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity implements StudentListAdapter.OnStudClick {


    ActivityViewStudentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fetchStudents();

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchStudents();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchStudents();
    }

    void fetchStudents(){

        binding.swipe.setRefreshing(true);
        FirebaseFirestore.getInstance()
                .collection("Student")
                .whereEqualTo("adminId", FirebaseAuth.getInstance().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                binding.swipe.setRefreshing(false);
                if(task.isSuccessful()){

                    List<Student> data = new ArrayList<>();
                    for(DocumentSnapshot doc : task.getResult()){
                        Student stud = doc.toObject(Student.class);
                        data.add(stud);
                    }

                    updateRecycler(data);
                } else {
                    Toast.makeText(StudentListActivity.this, "Failed to get data", Toast.LENGTH_SHORT).show();
                    Toast.makeText(StudentListActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    StudentListAdapter studentListAdapter;
    void updateRecycler(List<Student> data){

        if(studentListAdapter == null){
            studentListAdapter = new StudentListAdapter(this,this);
            binding.studRecycler.setAdapter(studentListAdapter);
            binding.studRecycler.setLayoutManager(new LinearLayoutManager(this));
        }

        studentListAdapter.updateList(data);
    }

    @Override
    public void onStudClick(Student data) {
        AppSingleton.getINSTANCE().setSelectedStudent(data);
        startActivity(new Intent(this, StudentDetailActivity.class));
    }

    @Override
    public void onStudDelete(Student student) {

    }
}