package com.agdyaputra.zooms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FoodManagement extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_management);

        mRecyclerView = findViewById(R.id.FoodrecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //send Queries
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mfirebaseDatabase.getReference("food");
    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<FoodModel, FoodViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<FoodModel, FoodViewHolder>( FoodModel.class,
                        R.layout.food_item,
                        FoodViewHolder.class,
                        mRef) {
                    @Override
                    protected void populateViewHolder(FoodViewHolder viewHolder, FoodModel model, int position) {
                        viewHolder.setDetails(getApplicationContext(),model.getFood_id(),model.getFood_name(),model.getStock());
                    }

                    // PASS DATA TO EDIT
                    @Override
                    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
                        FoodViewHolder foodviewHolder = super.onCreateViewHolder(parent, viewType);

                        foodviewHolder.setOnClickListener(new FoodViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView foodIDvw = view.findViewById(R.id.foodID);
                                TextView foodNamevw = view.findViewById(R.id.foodName);
                                TextView foodStockvw = view.findViewById(R.id.foodStock);
                                //get data
                                String foodID = foodIDvw.getText().toString();
                                String foodName = foodNamevw.getText().toString();
                                String foodStock = foodStockvw.getText().toString();
                                // passing data var
                                Intent intent = new Intent(view.getContext(), FoodEdit.class);
                                intent.putExtra("FoodID", foodID);
                                intent.putExtra("FoodName", foodName);
                                intent.putExtra("FoodStock", foodStock);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                // will be concluded!
                            }
                        });

                        return foodviewHolder;
                    }

                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}
