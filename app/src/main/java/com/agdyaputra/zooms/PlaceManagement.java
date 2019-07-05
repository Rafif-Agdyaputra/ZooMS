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

public class PlaceManagement extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_management);

        mRecyclerView = findViewById(R.id.PlacerecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //send Queries
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mfirebaseDatabase.getReference("place");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<PlaceModel, PlaceViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<PlaceModel, PlaceViewHolder>(PlaceModel.class,
                        R.layout.place_item,
                        PlaceViewHolder.class,
                        mRef) {
                    @Override
                    protected void populateViewHolder(PlaceViewHolder viewHolder, PlaceModel model, int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getPlace_id(), model.getPlace_name(), model.getHabitat());
                    }

                    // PASS DATA TO EDIT
                    @Override
                    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
                        PlaceViewHolder placeviewHolder = super.onCreateViewHolder(parent, viewType);

                        placeviewHolder.setOnClickListener(new PlaceViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView placeIDvw = view.findViewById(R.id.placeID);
                                TextView placeNamevw = view.findViewById(R.id.placeName);
                                TextView placeHabitatvw = view.findViewById(R.id.placeHabitat);
                                //get data
                                String placeID = placeIDvw.getText().toString();
                                String placeName = placeNamevw.getText().toString();
                                String placeHabitat = placeHabitatvw.getText().toString();
                                // passing data var
                                Intent intent = new Intent(view.getContext(), PlaceEdit.class);
                                intent.putExtra("PlaceID", placeID);
                                intent.putExtra("PlaceName", placeName);
                                intent.putExtra("PlaceHabitat", placeHabitat);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                // will be concluded!
                            }
                        });

                        return placeviewHolder;
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}
