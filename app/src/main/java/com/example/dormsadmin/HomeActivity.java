package com.example.dormsadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dormsadmin.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cardRoomDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, RoomHomeActivity.class);
                startActivity(i);
            }
        });

        binding.food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, FoodActivity.class);
                startActivity(i);
            }
        });
        binding.studentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, StudentHomeActivity.class);
                startActivity(i);
            }
        });
        binding.queries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ComplaintsActivity.class);
                startActivity(i);
            }
        });

        binding.fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,FeesActivity.class));
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openlogin_dorms();
            }
        });

        binding.accodomation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,AccomodationActivity.class));
            }
        });

        binding.leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, LeaveListActivity.class));
            }
        });


    }

    public void openlogin_dorms(){
        SharedPreferences.Editor editor = getSharedPreferences("PREF_DORMS",MODE_PRIVATE).edit();
        editor.putBoolean("IS_LOGIN",false);
        editor.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
