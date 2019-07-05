package com.agdyaputra.zooms;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnimalEdit extends AppCompatActivity {

    EditText AnimalIdUI,AnimalNameUI, AnimalSpeciesUI;
    String Animal_Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_edit);
        //======== Set Connectivity Manager
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //=========
        Button btnAnimEdit = findViewById(R.id.btnEditAnim);
        Button btnAnimDelete = findViewById(R.id.btnDeleteAnim);
        final Spinner staticSpinner1 = findViewById(R.id.autoComplete_Gender);
        final Spinner staticSpinner2 = findViewById(R.id.autoComplete_Condition);
        final AutoCompleteTextView SpinnerAnimEdit = findViewById(R.id.HabitatEdit);
        AnimalIdUI = findViewById(R.id.animalIDEdit);
        AnimalNameUI = findViewById(R.id.AnimNameEdit);
        AnimalSpeciesUI = findViewById(R.id.AnimSpeciesEdit);

        //=======get data from intent=========
        String AnimalId = getIntent().getStringExtra("AnimalID");
        String AnimalName = getIntent().getStringExtra("AnimalName");
        String AnimalSpecies = getIntent().getStringExtra("AnimalSpecies");
        String AnimalGender = getIntent().getStringExtra("AnimalGender");
        String AnimalStatus = getIntent().getStringExtra("AnimalStatus");
        String AnimalPlacement = getIntent().getStringExtra("AnimalPlacement");

        //========set data to EditText views=======
        AnimalIdUI.setText(AnimalId);
        AnimalNameUI.setText(AnimalName);
        AnimalSpeciesUI.setText(AnimalSpecies);
        SpinnerAnimEdit.setText(AnimalPlacement);
        //========set data to EditText views=======

        //====================static Array for spinner ======================//
        ArrayAdapter<CharSequence> staticAdapter1 = ArrayAdapter
                .createFromResource(this, R.array.animalGender,
                        android.R.layout.simple_spinner_item);
        staticAdapter1
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner1.setAdapter(staticAdapter1);

        ArrayAdapter<CharSequence> staticAdapter2 = ArrayAdapter
                .createFromResource(this, R.array.animalCondition,
                        android.R.layout.simple_spinner_item);
        staticAdapter2
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner2.setAdapter(staticAdapter2);

        //====================static Array for spinner ========================//

        //=======set data to Spinner views====
        int staticSpinner1position = staticAdapter1.getPosition(AnimalGender);
        staticSpinner1.setSelection(staticSpinner1position);

        int staticSpinner2position = staticAdapter2.getPosition(AnimalStatus);
        staticSpinner2.setSelection(staticSpinner2position);
        //======set data to Spinner views=====

        //====== POPULATE AUTOCOMPLETE-TEXTVIEW WITH DATA ============//
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        //Create a new ArrayAdapter with your context and the simple layout for the dropdown menu provided by Android
        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line);
        database.child("place").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                autoComplete.clear();
                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()){
                    String suggestion = suggestionSnapshot.child("place_name").getValue(String.class);
                    autoComplete.add(suggestion);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        //========== DECLARE AUTOCOMPLETE TEXTVIEW. AND ADDING ADAPTER =============//
        SpinnerAnimEdit.setAdapter(autoComplete);

        SpinnerAnimEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerAnimEdit.showDropDown();
            }
        });

        //====== POPULATE AUTOCOMPLETE-TEXTVIEW WITH DATA ============//

        //============ BUTTON ONCLICK AND GET DATA FROM INPUT ============//
        btnAnimEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animal_Id = AnimalIdUI.getText().toString();
                final String Animal_Name = AnimalNameUI.getText().toString();
                final String Species = AnimalSpeciesUI.getText().toString();
                final String Gender = staticSpinner1.getSelectedItem().toString();
                final String Status = staticSpinner2.getSelectedItem().toString();
                final String Placement = SpinnerAnimEdit.getText().toString();
                if (cm.getActiveNetworkInfo() != null) {
                    if (Animal_Name.isEmpty()) {
                        AnimalNameUI.setError("Please enter Animal Name");
                        AnimalNameUI.requestFocus();
                    } else if (Species.isEmpty()) {
                        AnimalSpeciesUI.setError("Please enter Species Name");
                        AnimalSpeciesUI.requestFocus();
                    } else if (Placement.isEmpty()) {
                        SpinnerAnimEdit.setError("Please enter Placement Habitat");
                        SpinnerAnimEdit.requestFocus();
                    } else {
                        final DatabaseReference PlacenameCheck = FirebaseDatabase.getInstance().getReference().child("place");
                        PlacenameCheck.orderByChild("place_name").equalTo(Placement).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    //============================ CALL SUBMIT FUNCTION ===================================================================//
                                    submitEditAnimals(new AnimalModel(Animal_Id, Animal_Name, Species, Gender, Status, Placement));
                                    AnimManage();
                                    }
                                    else {
                                    Toast.makeText(getApplicationContext(), "Invalid Placement Habitat, No records found!", Toast.LENGTH_SHORT).show();
                                    }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Internet Connection Is Unavailable", Toast.LENGTH_SHORT).show();
                }

            }

        });
        //============ BUTTON ONCLICK AND GET DATA FROM INPUT ============//
        btnAnimDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cm.getActiveNetworkInfo() != null) {
                    Animal_Id = getIntent().getStringExtra("AnimalID");
                    final String AnimalName = getIntent().getStringExtra("AnimalName");
                    final String Species = getIntent().getStringExtra("AnimalSpecies");
                    final String Gender = getIntent().getStringExtra("AnimalGender");
                    final String Status = "Removed";
                    final String Placement = "Removed";
                    deleteAnimals(new AnimalModel(Animal_Id, AnimalName, Species, Gender, Status, Placement));
                    AnimManage();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Internet Connection Is Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //============ SUBMIT FUNCTION =================================//
    private void submitEditAnimals (AnimalModel requests) {
        FirebaseDatabase.getInstance().getReference("animal/" + Animal_Id).setValue(requests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Animal Data Changed!",Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteAnimals (AnimalModel requests) {
        FirebaseDatabase.getInstance().getReference("animal/" + Animal_Id).setValue(requests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Animal Data Deleted!",Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AnimManage(){
        finish();
    }
}
