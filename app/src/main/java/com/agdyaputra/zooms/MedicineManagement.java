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

public class MedicineManagement extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_management);

        mRecyclerView = findViewById(R.id.MedicinerecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //send Queries
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mfirebaseDatabase.getReference("medicine");
    }

    private void firebaseSearch(String searchText) {
        Query firebaseSearchQuery = mRef.orderByChild("med_name").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<MedicineModel, MedicineViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<MedicineModel, MedicineViewHolder>(
                        MedicineModel.class,
                        R.layout.medicine_item,
                        MedicineViewHolder.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(MedicineViewHolder viewHolder, MedicineModel model, int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getMed_id(), model.getMed_name(), model.getStock());
                    }

                    // PASS DATA TO EDIT
                    @Override
                    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        MedicineViewHolder medicineviewHolder = super.onCreateViewHolder(parent, viewType);

                        medicineviewHolder.setOnClickListener(new MedicineViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView MedIDvw = view.findViewById(R.id.medicineID);
                                TextView MedNamevw = view.findViewById(R.id.medicineName);
                                TextView MedStockvw = view.findViewById(R.id.medicineStock);
                                //get data
                                String medID = MedIDvw.getText().toString();
                                String medName = MedNamevw.getText().toString();
                                String medStock = MedStockvw.getText().toString();
                                // passing data var
                                Intent intent = new Intent(view.getContext(), MedicineEdit.class);
                                intent.putExtra("MedID", medID);
                                intent.putExtra("MedName", medName);
                                intent.putExtra("MedStock", medStock);
                                startActivity(intent);

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });

                        return medicineviewHolder;
                    }

                };

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<MedicineModel, MedicineViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<MedicineModel, MedicineViewHolder>(MedicineModel.class,
                        R.layout.medicine_item,
                        MedicineViewHolder.class,
                        mRef) {
                    @Override
                    protected void populateViewHolder(MedicineViewHolder viewHolder, MedicineModel model, int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getMed_id(), model.getMed_name(), model.getStock());
                    }

                    // PASS DATA TO EDIT
                    @Override
                    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        MedicineViewHolder medicineviewHolder = super.onCreateViewHolder(parent, viewType);

                        medicineviewHolder.setOnClickListener(new MedicineViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView MedIDvw = view.findViewById(R.id.medicineID);
                                TextView MedNamevw = view.findViewById(R.id.medicineName);
                                TextView MedStockvw = view.findViewById(R.id.medicineStock);
                                //get data
                                String medID = MedIDvw.getText().toString();
                                String medName = MedNamevw.getText().toString();
                                String medStock = MedStockvw.getText().toString();
                                // passing data var
                                Intent intent = new Intent(view.getContext(), MedicineEdit.class);
                                intent.putExtra("MedID", medID);
                                intent.putExtra("MedName", medName);
                                intent.putExtra("MedStock", medStock);
                                startActivity(intent);

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });

                        return medicineviewHolder;
                    }

                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu, adds items to action bar if present
        getMenuInflater().inflate(R.menu.medicine_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search_med);
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
}
