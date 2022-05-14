package com.example.dormsadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dormsadmin.Model.Room;
import com.example.dormsadmin.databinding.RoomListLayoutBinding;

import java.util.ArrayList;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.MyViewHolder> {

    Context context;
    ArrayList<Room> userArrayList = new ArrayList<>();
    RoomListAction listner;

    String type = "ROOM_LIST";

    public RoomListAdapter(Context context) {
        this.context = context;
    }

    public RoomListAdapter(Context context, RoomListAction listner, String type) {
        this.context = context;
        this.listner = listner;
        this.type = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RoomListLayoutBinding binding = RoomListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Room user = userArrayList.get(position);

        holder.binding.rroomName.setText(user.getName());
        holder.binding.rbeds.setText(user.getBeds());
        holder.binding.rrent.setText(String.valueOf(user.getRent()));

        if(type.equals("ACCOMODATION")){
            holder.binding.bedParent.setVisibility(View.GONE);
            holder.binding.rentParent.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onRoomListClick(user);
                }
            });
        }

    }

    void updateList(ArrayList<Room> list){
        this.userArrayList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        RoomListLayoutBinding binding;
        public MyViewHolder(@NonNull RoomListLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

    public interface RoomListAction {
        void onRoomListClick(Room room);
    }
}
