package com.agdyaputra.zooms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout buttonAnim;
    private LinearLayout buttonFood;
    private LinearLayout buttonPlace;
    private LinearLayout buttonMedicine;
    String Dept_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Dept_name = getIntent().getStringExtra("Dept_Name");


        buttonAnim = findViewById(R.id.anim_menu);
        buttonAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Dept_name.equals("Keeper")){
                Animalmenu();
                }
                else if (Dept_name.equals("All")) {
                    Animalmenu();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Unauthorized Access!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonFood = findViewById(R.id.food_menu);
        buttonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Dept_name.equals("Feeder")){
                    Foodmenu();
                }
                else if (Dept_name.equals("All")) {
                    Foodmenu();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Unauthorized Access!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonPlace = findViewById(R.id.place_menu);
        buttonPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Dept_name.equals("Keeper")){
                    Placemenu();
                }else if (Dept_name.equals("All")) {
                    Placemenu();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Unauthorized Access!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonMedicine = findViewById(R.id.medicine_menu);
        buttonMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Dept_name.equals("Doctor")){
                    Medicinemenu();
                }else if (Dept_name.equals("All")) {
                    Medicinemenu();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Unauthorized Access!", Toast.LENGTH_SHORT).show();
                }
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

    public void Medicinemenu(){
        Intent intent = new Intent(this, MedicineActivity.class);
        startActivity(intent);
    }
}
