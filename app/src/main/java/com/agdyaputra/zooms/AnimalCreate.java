package com.agdyaputra.zooms;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnimalCreate extends AppCompatActivity {

    String Animal_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_create);
        Animal_Id = "";

        //======== Set Connectivity Manager ==========//
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //============================================//
        final Spinner staticSpinner1 = findViewById(R.id.static_spinner1);
        final Spinner staticSpinner2 = findViewById(R.id.static_spinner2);
        Button btnAnimSubmit = findViewById(R.id.btnCreateAnim);
        final EditText AnimName = findViewById(R.id.AnimNameCreate);
        final EditText Animspecies = findViewById(R.id.AnimSpeciesCreate);


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
        //====== POPULATE AUTOCOMPLETE-TEXTVIEW WITH DATA ============//

        //========== DECLARE AUTOCOMPLETE TEXTVIEW. AND ADDING ADAPTER =============//
        final AutoCompleteTextView Spinner3 = findViewById(R.id.static_spinner3);
        Spinner3.setAdapter(autoComplete);

        Spinner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner3.showDropDown();
            }
        });

        //====== POPULATE AUTOCOMPLETE-TEXTVIEW WITH DATA ============//

        //============ BUTTON ONCLICK AND GET DATA FROM INPUT ============//
        btnAnimSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String AnimalName = AnimName.getText().toString();
                final String Species = Animspecies.getText().toString();
                final String Gender = staticSpinner1.getSelectedItem().toString();
                final String Status = staticSpinner2.getSelectedItem().toString();
                final String Placement = Spinner3.getText().toString();
                if (cm.getActiveNetworkInfo() != null) {
                    if (AnimalName.isEmpty()) {
                        AnimName.setError("Please enter Animal Name");
                        AnimName.requestFocus();
                    } else if (Species.isEmpty()) {
                        Animspecies.setError("Please enter Species Name");
                        Animspecies.requestFocus();
                    } else if (Placement.isEmpty()) {
                        Spinner3.setError("Please enter Placement Habitat");
                        Spinner3.requestFocus();
                    } else {
                        Animal_Id = FirebaseDatabase.getInstance().getReference().push().getKey();
                        final DatabaseReference AnimNameCheck = FirebaseDatabase.getInstance().getReference().child("animal");
                        AnimNameCheck.orderByChild("animal_name").equalTo(AnimalName).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    Toast.makeText(getApplicationContext(), "Animal Name Is Already Exists!", Toast.LENGTH_SHORT).show();
                                }else{
                                    final DatabaseReference PlacenameCheck = FirebaseDatabase.getInstance().getReference().child("place");
                                    PlacenameCheck.orderByChild("place_name").equalTo(Placement).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()){
                                                //============================ CALL SUBMIT FUNCTION ===================================================================//
                                                submitAnimals(new AnimalModel(Animal_Id, AnimalName, Species, Gender, Status, Placement));
                                                finish();
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
    }
    //============ SUBMIT FUNCTION =================================//
    private void submitAnimals (AnimalModel requests) {
        FirebaseDatabase.getInstance().getReference("animal/" + Animal_Id).setValue(requests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Animal_Id="";
                Toast.makeText(getApplicationContext(),"Animal Added to Collection!",Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
