package com.example.dormsadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dormsadmin.Model.Student;
import com.example.dormsadmin.databinding.DialogStudentListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentListDialog extends DialogFragment implements StudentListAdapter.OnStudClick {

    DialogStudentListBinding binding;
    List<Student> presentList;

    public StudentListDialog(List<Student> presentList) {
        this.presentList = presentList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogStudentListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchStudents();
    }


    void fetchStudents(){

        FirebaseFirestore.getInstance()
                .collection("Student")
                .whereEqualTo("adminId", FirebaseAuth.getInstance().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){

                    List<Student> data = new ArrayList<>();
                    for(DocumentSnapshot doc : task.getResult()){

                        Student stud = doc.toObject(Student.class);
                        Boolean IS_PRESENT = false;

                        if(presentList != null){
                            for(Student s : presentList){
                                if(s.getStudId().equals(stud.getStudId()))
                                    IS_PRESENT = true;
                            }
                        }

                        if(!IS_PRESENT)
                            data.add(stud);
                    }

                    updateRecycler(data);
                }
            }
        });
    }

    StudentListAdapter studentListAdapter;
    void updateRecycler(List<Student> data){

        if(studentListAdapter == null){
            studentListAdapter = new StudentListAdapter(getContext(),this);
            binding.studRecycler.setAdapter(studentListAdapter);
            binding.studRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        studentListAdapter.updateList(data);
    }

    @Override
    public void onStudClick(Student data) {
        AppSingleton.getINSTANCE().addToStudentList(data);
        dismiss();
    }

    @Override
    public void onStudDelete(Student student) {

    }
}
