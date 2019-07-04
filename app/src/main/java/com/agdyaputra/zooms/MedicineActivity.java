package com.agdyaputra.zooms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MedicineActivity extends AppCompatActivity {

    private LinearLayout btnCreateMedicine;
    private LinearLayout btnManageMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        btnCreateMedicine = findViewById(R.id.BtnMedNew);
        btnCreateMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineCreate();
            }
        });
        btnManageMedicine = findViewById(R.id.BtnMedManage);
        btnManageMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineManage();
            }
        });
    }

    public void MedicineCreate(){
        Intent intent = new Intent(this, MedicineCreate.class);
        startActivity(intent);
    }

    public void MedicineManage(){
        Intent intent = new Intent(this, MedicineManagement.class);
        startActivity(intent);
    }
}
