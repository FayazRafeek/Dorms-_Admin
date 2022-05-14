package com.example.dormsadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dormsadmin.Model.Complaint;
import com.example.dormsadmin.databinding.ComplaintListLayoutBinding;
import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {

    List<Complaint> complaints =  new ArrayList();
    ComplaintAction listner;

    public ComplaintAdapter(ComplaintAction listner) {
        this.listner = listner;
    }

    public ComplaintAdapter() {
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ComplaintListLayoutBinding binding = ComplaintListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ComplaintAdapter.ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        Complaint add_complaint=complaints.get(position);

        Date date = new Date();
        date.setTime(Long.parseLong(add_complaint.getTimestamp()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String time = dateFormat.format(date);

        holder.binding.timeTxt.setText(time);
        holder.binding.vComplaint.setText(add_complaint.getStudName());
        String msg = add_complaint.getComplaint();
        if(msg != null && msg.length() > 25)
            msg = msg.substring(0,25);
        holder.binding.vComplaintTxt.setText(msg.trim());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onCompClick(add_complaint);
            }
        });

    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }
    public void updateList(List<Complaint> complaints){
        this.complaints = complaints;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        ComplaintListLayoutBinding binding;

        public ViewHolder(@NonNull @NotNull ComplaintListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }


    public interface ComplaintAction {
        public void onCompClick(Complaint complaint);
    }
}
