package com.example.dormsadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dormsadmin.Model.Student;
import com.example.dormsadmin.databinding.StudentListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentListVH> {

    Context context;
    List<Student> list = new ArrayList<>();
    OnStudClick listner;
    String type = "NORMAL_LIST";

    public StudentListAdapter(Context context, OnStudClick listner) {
        this.context = context;
        this.listner = listner;
    }


    public StudentListAdapter(Context context, OnStudClick listner, String type) {
        this.context = context;
        this.listner = listner;
        this.type = type;
    }

    @NonNull
    @Override
    public StudentListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StudentListItemBinding binding = StudentListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new StudentListVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListVH holder, int position) {

        Student student = list.get(position);

        holder.binding.studName.setText(student.getFull_name());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onStudClick(student);
            }
        });

        if(type.equals("ACCOMODATION")){
            holder.binding.nextBtn.setVisibility(View.GONE);
            holder.binding.deleteBtn.setVisibility(View.VISIBLE);

            holder.binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onStudDelete(student);
                }
            });
        }  else if(type.equals("MENU_SELECTION")){
            holder.binding.itemsCount.setVisibility(View.VISIBLE);
            String ct = student.getPhone() + " Items";
            holder.binding.itemsCount.setText(ct);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void updateList(List<Student> items){
        list = items;
        notifyDataSetChanged();
    }

    class StudentListVH extends RecyclerView.ViewHolder{

        StudentListItemBinding binding;

        public StudentListVH(@NonNull StudentListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnStudClick {
        void onStudClick(Student data);
        void onStudDelete(Student student);
    }
}
