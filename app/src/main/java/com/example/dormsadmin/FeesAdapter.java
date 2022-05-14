package com.example.dormsadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dormsadmin.Model.FeeReceipt;
import com.example.dormsadmin.databinding.FeesListItemBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FeesAdapter extends RecyclerView.Adapter<FeesAdapter.FeesVH> {

    Context context;
    List<FeeReceipt> receipts = new ArrayList<>();

    public FeesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FeesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FeesListItemBinding binding = FeesListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FeesVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeesVH holder, int position) {

        FeeReceipt item = receipts.get(position);
        holder.binding.monthYear.setText(item.getMonth() + " " + item.getYear());
        holder.binding.totalAmt.setText("₹" + item.gettAmt());

        holder.binding.name.setText(item.getStudentName());

        Date date = new Date(); date.setTime(Long.valueOf(item.getTs()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String payDate = sdf.format(date);

        holder.binding.paidDate.setText(payDate);

    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public void updateList(List<FeeReceipt> list){
        receipts = list;
        notifyDataSetChanged();
    }

    class FeesVH extends RecyclerView.ViewHolder{

        FeesListItemBinding binding;
        public FeesVH(@NonNull FeesListItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
