package com.example.dormsadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FoodActivity extends AppCompatActivity {

    CardView break_card;
    CardView lunch_card;
    CardView dinner_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        break_card= findViewById(R.id.break_card);
        lunch_card = findViewById(R.id.lunch_card);
        dinner_card = findViewById(R.id.dinner_card);

        break_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodActivity.this, BreakfastActivity.class);
                startActivity(i);
            }
        });
        lunch_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodActivity.this, LunchActivity.class);
                startActivity(i);
            }
        });
        dinner_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodActivity.this, DinnerActivity.class);
                startActivity(i);
            }
        });


    }
}