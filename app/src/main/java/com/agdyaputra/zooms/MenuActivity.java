package com.agdyaputra.zooms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout buttonAnim;
    private LinearLayout buttonFood;
    private LinearLayout buttonPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonAnim = findViewById(R.id.anim_menu);
        buttonAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animalmenu();
            }
        });
        buttonFood = findViewById(R.id.food_menu);
        buttonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Foodmenu();
            }
        });
        buttonPlace = findViewById(R.id.place_menu);
        buttonPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Placemenu();
            }
        });

    }

    public void Animalmenu(){
        Intent intent = new Intent(this, AnimalActivity.class);
        startActivity(intent);
    }

    public void Foodmenu(){
        Intent intent = new Intent(this, FoodActivity.class);
        startActivity(intent);
    }

    public void Placemenu(){
        Intent intent = new Intent(this, PlaceActivity.class);
        startActivity(intent);
    }
}
