package com.agdyaputra.zooms;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

public class FoodEdit extends AppCompatActivity {

    EditText FoodIdUI,FoodNameUI, FoodStockUI, FStockUI;
    String Food_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);

        //======== Set Connectivity Manager//
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //=========//
        Button btnFoodAdd = findViewById(R.id.btnFoodAdd);
        Button btnFoodTake = findViewById(R.id.btnFoodTake);

        FoodIdUI = findViewById(R.id.food_id);
        FoodNameUI = findViewById(R.id.food_name);
        FoodStockUI = findViewById(R.id.food_current_stock);
        FStockUI = findViewById(R.id.food_stock);

        //=======get data from intent=========//
        final String FoodId = getIntent().getStringExtra("FoodID");
        final String FoodName = getIntent().getStringExtra("FoodName");
        final String FoodStock = getIntent().getStringExtra("FoodStock");

        //========set data to EditText views=======//
        FoodIdUI.setText(FoodId);
        FoodNameUI.setText(FoodName);
        FoodStockUI.setText(FoodStock);
        //========set data to EditText views=======//

        //========= Add Function ========//
        btnFoodAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food_Id = FoodIdUI.getText().toString();
                final String Food_Name = FoodNameUI.getText().toString();

                if (cm.getActiveNetworkInfo() != null) {
                    if (FStockUI.getText().toString().isEmpty()) {
                        FStockUI.setError("Please Enter Stock Values");
                        FStockUI.requestFocus();
                    }
                    else {
                        final int FoodCurrStock = Integer.parseInt(FoodStockUI.getText().toString());
                        final int Stock = Integer.parseInt(FStockUI.getText().toString());
                        final String result = String.valueOf(FoodCurrStock + Stock);
                        if (Stock <= 0) {
                            FStockUI.setError("Stock Cannot be lower than 1");
                            FStockUI.requestFocus();
                        } else {
                            //============================ CALL SUBMIT FUNCTION ===================================================================//
                            submitAddFood(new FoodModel(Food_Id, Food_Name, result));
                            FoodStockUI.setText(result);
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Internet Connection Is Unavailable", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //========= Take Function ========//
        btnFoodTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food_Id = FoodIdUI.getText().toString();
                final String Food_Name = FoodNameUI.getText().toString();

                if (cm.getActiveNetworkInfo() != null) {
                    if (FStockUI.getText().toString().isEmpty()) {
                        FStockUI.setError("Please Enter Stock Values");
                        FStockUI.requestFocus();
                    }else {
                        final int FoodCurrStock = Integer.parseInt(FoodStockUI.getText().toString());
                        final int Stock = Integer.parseInt(FStockUI.getText().toString());
                        final String result = String.valueOf(FoodCurrStock - Stock);
                        if (Stock <= 0) {
                            FStockUI.setError("Stock Cannot be lower than 1");
                            FStockUI.requestFocus();
                        } else if(Integer.parseInt(result)<=0){
                            FStockUI.setError("Current stock cannot be lower than 0");
                            FStockUI.requestFocus();
                        }
                        else {
                            //============================ CALL SUBMIT FUNCTION ===================================================================//
                            submitTakeFood(new FoodModel(Food_Id, Food_Name, result));
                            FoodStockUI.setText(result);
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Internet Connection Is Unavailable", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //============ SUBMIT FUNCTION =================================//
    private void submitAddFood (FoodModel requests) {
        FirebaseDatabase.getInstance().getReference("food/" + Food_Id).setValue(requests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Food Data added!",Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitTakeFood (FoodModel requests) {
        FirebaseDatabase.getInstance().getReference("food/" + Food_Id).setValue(requests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Food Data taken!",Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
