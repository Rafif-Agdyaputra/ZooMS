package com.agdyaputra.zooms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class FoodActivity extends Activity {

    private LinearLayout btnManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        btnManage = findViewById(R.id.BtnFoodManage);
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodManage();
            }
        });

    }

    public void FoodManage(){
        Intent intent = new Intent(this, FoodManagement.class);
        startActivity(intent);
    }
}
