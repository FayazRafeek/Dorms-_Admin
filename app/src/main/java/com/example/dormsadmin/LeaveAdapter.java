package com.example.dormsadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dormsadmin.Model.Leave;
import com.example.dormsadmin.databinding.LeaveListItemBinding;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.ViewHolder>{

    Context context;
    List<Leave>list =  new ArrayList();
    LeaveAction listner;

    public LeaveAdapter(Context context) {
        this.context = context;
    }

    public LeaveAdapter(Context context, LeaveAction listner) {
        this.context = context;
        this.listner = listner;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LeaveListItemBinding binding = LeaveListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        Leave leave= list.get(position);

        holder.binding.status.setText(leave.getStatus());
        holder.binding.progress.setVisibility(View.GONE);
        switch (leave.getStatus()){
            case "SUBMITTED" :
                holder.binding.status.setTextColor(ContextCompat.getColor(context,R.color.grey_5));
                holder.binding.btnParent.setVisibility(View.VISIBLE);
                break;
            case "VIEWED" :
                holder.binding.status.setTextColor(ContextCompat.getColor(context,R.color.orange));
                holder.binding.btnParent.setVisibility(View.GONE);
                break;
            case "APPROVED" :
                holder.binding.status.setTextColor(ContextCompat.getColor(context,R.color.green));
                holder.binding.btnParent.setVisibility(View.GONE);
                break;
            case "REJECTED" :
                holder.binding.status.setTextColor(ContextCompat.getColor(context,R.color.peach));
                holder.binding.btnParent.setVisibility(View.GONE);
                break;
        }

        holder.binding.reason.setText(leave.getReason().trim());

        holder.binding.fromDate.setText(leave.getFromDate());
        holder.binding.toDate.setText(leave.getToDate());

        holder.binding.student.setText(leave.getStudentName());

        holder.binding.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.progress.setVisibility(View.VISIBLE);
                listner.onApprove(leave);
            }
        });
        holder.binding.rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.progress.setVisibility(View.VISIBLE);
                listner.onReject(leave);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void updateList(List<Leave> lists){
        this.list = lists;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        LeaveListItemBinding binding;

        public ViewHolder(@NonNull @NotNull LeaveListItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    public interface LeaveAction {
        void onApprove(Leave leave);
        void onReject(Leave leave);
    }
}
