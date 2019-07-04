package com.agdyaputra.zooms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class AnimalActivity extends AppCompatActivity {

    private LinearLayout btnCreate;
    private LinearLayout btnManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        btnCreate = findViewById(R.id.BtnAnimNew);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimCreate();
            }
        });
        btnManage = findViewById(R.id.BtnAnimManage);
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimManage();
            }
        });
    }

    public void AnimCreate(){
        Intent intent = new Intent(this, AnimalCreate.class);
        startActivity(intent);
    }

    public void AnimManage(){
        Intent intent = new Intent(this, AnimalManagement.class);
        startActivity(intent);
    }
}
