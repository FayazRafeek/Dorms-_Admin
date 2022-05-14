package com.example.dormsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dormsadmin.Model.FoodItem;
import com.example.dormsadmin.Model.MenuSelection;
import com.example.dormsadmin.databinding.ActivityViewBreakfastBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MenuListActivity extends AppCompatActivity implements MenuAdapter.FoodItemAction {

    ActivityViewBreakfastBinding binding;
    String type = "Breakfast";
    Boolean IS_VIEW = false;
    MenuSelection selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewBreakfastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = getIntent().getStringExtra("TYPE");
        binding.typeHeader.setText(type + " Menu");

        if(getIntent().getBooleanExtra("IS_SELECTION",false)){
            selection = AppSingleton.getINSTANCE().getSelectedMenu();
            binding.constraintLayout2.setVisibility(View.GONE);
            if (selection != null)
                updateRecyclerView(selection.getFoodItems());
            else
                finish();
        } else
            fetchMenu();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.bfSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!IS_VIEW)
                fetchMenu();
                else binding.bfSwipe.setRefreshing(false);
            }
        });
    }


    void fetchMenu(){

        binding.bfSwipe.setRefreshing(true);
        FirebaseFirestore.getInstance()
                .collection("Admin")
                .document(FirebaseAuth.getInstance().getUid())
                .collection(type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        binding.bfSwipe.setRefreshing(false);
                        if(task.isSuccessful()){

                            List<FoodItem> items = new ArrayList<>();
                            for(DocumentSnapshot doc : task.getResult()){
                                FoodItem item = doc.toObject(FoodItem.class);
                                items.add(item);
                            }

                            updateRecyclerView(items);
                        } else {
                            Toast.makeText(MenuListActivity.this, "fauled to get menu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    MenuAdapter adapter;
    void updateRecyclerView(List<FoodItem> items){

        if(adapter == null){
            adapter = new MenuAdapter(this,this);
            binding.menuRecycler.setAdapter(adapter);
            binding.menuRecycler.setLayoutManager(new LinearLayoutManager(this));
        }

        adapter.updateList(items);
    }

    @Override
    public void foodDelete(FoodItem foodItem) {

        Toast.makeText(MenuListActivity.this, "Deleting item", Toast.LENGTH_SHORT).show();
        FirebaseFirestore
                .getInstance()
                .collection("Admin")
                .document(FirebaseAuth.getInstance().getUid())
                .collection(type)
                .document(foodItem.getItemId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MenuListActivity.this, "Delete successfull", Toast.LENGTH_SHORT).show();
                            fetchMenu();
                        } else {
                            Toast.makeText(MenuListActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MenuListActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}