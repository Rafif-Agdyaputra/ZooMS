package com.agdyaputra.zooms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PlaceManagement extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mRef;
    static String child = "place_name";

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

    //==============================search data=================================//
    private void firebaseSearch(String searchText){
        Query firebaseSearchQuery = mRef.orderByChild(child).startAt(searchText).endAt(searchText+"\uf8ff");

        FirebaseRecyclerAdapter<PlaceModel, PlaceViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<PlaceModel, PlaceViewHolder>(
                        PlaceModel.class,
                        R.layout.place_item,
                        PlaceViewHolder.class,
                        firebaseSearchQuery
                ) {
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

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu, adds items to action bar if present
        getMenuInflater().inflate(R.menu.place_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search_place);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        //handle other action bar
        if (id == R.id.SearchPlaceName){
            child = "place_name";
            return true;
        } else if(id == R.id.SearchHabitat) {
            child = "habitat";
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
