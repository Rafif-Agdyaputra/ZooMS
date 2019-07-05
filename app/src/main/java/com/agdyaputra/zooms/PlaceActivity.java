package com.agdyaputra.zooms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class PlaceActivity extends AppCompatActivity {

    private LinearLayout btnCreatePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        btnCreatePlace = findViewById(R.id.BtnPlaceNew);
        btnCreatePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceCreate();
            }
        });
    }

    public void PlaceCreate(){
        Intent intent = new Intent(this, PlaceCreate.class);
        startActivity(intent);
    }
}
