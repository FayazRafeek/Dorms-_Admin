package com.example.dormsadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.dormsadmin.Model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    List<FoodItem> mlist = new ArrayList<>();
    Context context;
    FoodItemAction listner;

    public MenuAdapter(Context context, FoodItemAction listner) {
        this.context = context;
        this.listner = listner;
    }

    public MenuAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemb,parent,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FoodItem item = mlist.get(position);

        Glide.with(context)
                .load(item.getImgUrl())
                .centerCrop()
                .into(holder.i1);

        holder.text_itemname.setText(item.getItemName());
        holder.text_price.setText(item.getPrice());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.foodDelete(item);
            }
        });

    }

    public void updateList(List<FoodItem> list) {
        this.mlist = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView i1,deleteBtn;
        TextView text_itemname,text_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            i1 = itemView.findViewById(R.id.breakimage);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
            text_itemname = itemView.findViewById(R.id.text_itemname);
            text_price = itemView.findViewById(R.id.text_price);

        }
    }

    public interface FoodItemAction {
        void foodDelete(FoodItem foodItem);
    }
}
