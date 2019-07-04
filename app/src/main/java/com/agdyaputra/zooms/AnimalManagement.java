package com.agdyaputra.zooms;


import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AnimalManagement extends AppCompatActivity {

   RecyclerView mRecyclerView;
   FirebaseDatabase mfirebaseDatabase;
   DatabaseReference mRef;
   static String child = "animal_name";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_animal_management);

       mRecyclerView = findViewById(R.id.recyclerView);
       mRecyclerView.setHasFixedSize(true);

       mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

       //send Queries
       mfirebaseDatabase = FirebaseDatabase.getInstance();
       mRef = mfirebaseDatabase.getReference("animal");

   }

   //==============================search data=================================//
    private void firebaseSearch(String searchText){
        Query firebaseSearchQuery = mRef.orderByChild(child).startAt(searchText).endAt(searchText+"\uf8ff");

        FirebaseRecyclerAdapter<AnimalModel, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<AnimalModel, ViewHolder>(
                        AnimalModel.class,
                        R.layout.animal_item,
                        ViewHolder.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, AnimalModel model, int position) {
                        viewHolder.setDetails(getApplicationContext(),model.getAnimal_id(),model.getAnimal_name(),model.getSpecies(),
                                model.getGender(),model.getPlace_name(),model.getStatus());
                    }

                    // PASS DATA TO EDIT
                    @Override
                    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView animIDvw = view.findViewById(R.id.animalID);
                                TextView animNamevw = view.findViewById(R.id.animalName);
                                TextView animSpeciesvw = view.findViewById(R.id.animalSpecies);
                                TextView animGendervw = view.findViewById(R.id.animalGender);
                                TextView animStatusvw = view.findViewById(R.id.animalCondition);
                                TextView animPlacevw = view.findViewById(R.id.animalPlacement);
                                //get data
                                String animID = animIDvw.getText().toString();
                                String animName = animNamevw.getText().toString();
                                String animSpecies = animSpeciesvw.getText().toString();
                                String animGender = animGendervw.getText().toString();
                                String animStatus = animStatusvw.getText().toString();
                                String animPlace = animPlacevw.getText().toString();
                                // passing data var
                                Intent intent = new Intent(view.getContext(), AnimalEdit.class);
                                intent.putExtra("AnimalID", animID);
                                intent.putExtra("AnimalName", animName);
                                intent.putExtra("AnimalSpecies", animSpecies);
                                intent.putExtra("AnimalGender", animGender);
                                intent.putExtra("AnimalStatus", animStatus);
                                intent.putExtra("AnimalPlacement", animPlace);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                // will be concluded!
                            }
                        });

                        return viewHolder;
                    }
                    //PASS DATA TO EDIT

                };

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

   //========================load data into recyler view onstart=========================//
    @Override
    protected void onStart(){
       super.onStart();
        FirebaseRecyclerAdapter<AnimalModel, ViewHolder> firebaseRecyclerAdapter =
               new FirebaseRecyclerAdapter<AnimalModel, ViewHolder>(
                       AnimalModel.class,
                       R.layout.animal_item,
                       ViewHolder.class,
                       mRef
               ) {
                   @Override
                   protected void populateViewHolder(ViewHolder viewHolder, AnimalModel model, int position) {
                       viewHolder.setDetails(getApplicationContext(),model.getAnimal_id(),model.getAnimal_name(),model.getSpecies(),
                               model.getGender(),model.getPlace_name(),model.getStatus());
                   }

                   // PASS DATA TO EDIT
                   @Override
                   public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
                       ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                       viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                           @Override
                           public void onItemClick(View view, int position) {
                               TextView animIDvw = view.findViewById(R.id.animalID);
                               TextView animNamevw = view.findViewById(R.id.animalName);
                               TextView animSpeciesvw = view.findViewById(R.id.animalSpecies);
                               TextView animGendervw = view.findViewById(R.id.animalGender);
                               TextView animStatusvw = view.findViewById(R.id.animalCondition);
                               TextView animPlacevw = view.findViewById(R.id.animalPlacement);
                               //get data
                               String animID = animIDvw.getText().toString();
                               String animName = animNamevw.getText().toString();
                               String animSpecies = animSpeciesvw.getText().toString();
                               String animGender = animGendervw.getText().toString();
                               String animStatus = animStatusvw.getText().toString();
                               String animPlace = animPlacevw.getText().toString();
                               // passing data var
                               Intent intent = new Intent(view.getContext(), AnimalEdit.class);
                               intent.putExtra("AnimalID", animID);
                               intent.putExtra("AnimalName", animName);
                               intent.putExtra("AnimalSpecies", animSpecies);
                               intent.putExtra("AnimalGender", animGender);
                               intent.putExtra("AnimalStatus", animStatus);
                               intent.putExtra("AnimalPlacement", animPlace);
                               startActivity(intent);
                           }

                           @Override
                           public void onItemLongClick(View view, int position) {
                               // will be concluded!
                           }
                       });

                       return viewHolder;
                   }
                   //PASS DATA TO EDIT
               };

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
       //inflate the menu, adds items to action bar if present
       getMenuInflater().inflate(R.menu.animal_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search_anim);
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
        if (id == R.id.SearchSpecies){
            child = "species";
            return true;
        } else if(id == R.id.SearchAnimName) {
            child = "animal_name";
            return true;
        } else if(id == R.id.SearchGender) {
            child = "gender";
            return true;
        } else if(id == R.id.SearchPlacement) {
            child = "place_name";
            return true;
        } else if(id == R.id.SearchStatus) {
            child = "status";
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
