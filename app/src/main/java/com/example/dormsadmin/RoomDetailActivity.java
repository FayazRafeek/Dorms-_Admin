package com.example.dormsadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dormsadmin.Model.Room;
import com.example.dormsadmin.Model.Student;
import com.example.dormsadmin.databinding.ActivityRoomDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;

public class RoomDetailActivity extends AppCompatActivity implements StudentListAdapter.OnStudClick {

    ActivityRoomDetailBinding binding;
    Room room;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRoomDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        room = AppSingleton.getINSTANCE().getSelectedRoom();
        if(room == null) finish();

        binding.rroomName.setText(room.getName());
        binding.rbeds.setText(room.getBeds());
        binding.rrent.setText(room.getRent());

        AppSingleton.getINSTANCE().setStudentList(room.getStudents());

        binding.studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(AppSingleton.getINSTANCE().getStudentList() != null && AppSingleton.getINSTANCE().getStudentList().size() >= Integer.valueOf(room.getBeds())){
                    Toast.makeText(RoomDetailActivity.this, "Maximum beds reached", Toast.LENGTH_SHORT).show();
                    return;
                }
                StudentListDialog dialog = new StudentListDialog(room.getStudents());
                dialog.show(getSupportFragmentManager(),"TAG");
            }
        });

        AppSingleton.getINSTANCE().getGLOBAL_LIVE().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s){
                    case "STUDENT_LIST_UPDATE" : updateRecycler();
                }
            }
        });

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitStudentList();
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRoom();
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    StudentListAdapter studentListAdapter;
    void updateRecycler(){

        if(studentListAdapter == null){
            studentListAdapter = new StudentListAdapter(this,this,"ACCOMODATION");
            binding.studListRecycler.setAdapter(studentListAdapter);
            binding.studListRecycler.setLayoutManager(new LinearLayoutManager(this));
        }

        studentListAdapter.updateList(AppSingleton.getINSTANCE().getStudentList());
    }


    void submitStudentList(){

        Toast.makeText(this, "Updating student list", Toast.LENGTH_SHORT).show();
        room.setStudents(AppSingleton.getINSTANCE().getStudentList());
        FirebaseFirestore.getInstance()
                .collection("Admin")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Rooms")
                .document(room.getRoomId())
                .set(room)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            updateAddedStudents();
                        } else {
                            Toast.makeText(RoomDetailActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RoomDetailActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    void updateAddedStudents(){

        List<Student> newStudent = AppSingleton.getINSTANCE().newStudentRoomList;

        if(newStudent != null && newStudent.size() > 0) {
            WriteBatch batch = FirebaseFirestore.getInstance().batch();
            for (Student s : newStudent) {
                DocumentReference ref = FirebaseFirestore.getInstance().collection("Student").document(s.getStudId());
                batch.update(ref, "room", room.getRoomId());
            }
            batch.commit()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            updateDeletedStudent();
                        }
                    });
        } else updateDeletedStudent();
    }

    void updateDeletedStudent(){

        List<Student> deleteStu = AppSingleton.getINSTANCE().deletedStudent;

        if(deleteStu != null && deleteStu.size() > 0) {
            WriteBatch batch = FirebaseFirestore.getInstance().batch();
            for (Student s : deleteStu) {
                DocumentReference ref = FirebaseFirestore.getInstance().collection("Student").document(s.getStudId());
                batch.update(ref, "room", null);
            }
            batch.commit()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(RoomDetailActivity.this, "Room data updated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        } else {
            Toast.makeText(RoomDetailActivity.this, "Room data updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onStudClick(Student data) {
        AppSingleton.getINSTANCE().setSelectedStudent(data);
        startActivity(new Intent(this, StudentDetailActivity.class));
    }

    @Override
    public void onStudDelete(Student student) {
        AppSingleton.getINSTANCE().deleteFromStudentList(student);
    }

    void deleteRoom(){
        Toast.makeText(this, "Deleteing room", Toast.LENGTH_SHORT).show();
        FirebaseFirestore.getInstance()
                .collection("Admin")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Rooms")
                .document(room.getRoomId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RoomDetailActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RoomDetailActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
