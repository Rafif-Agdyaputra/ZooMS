package com.agdyaputra.zooms;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class PlaceEdit extends AppCompatActivity {

    EditText PlaceIdUI,PlaceNameUI, PlaceHabitatUI, NewHabitatUI;
    String Place_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_edit);

        //======== Set Connectivity Manager//
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //=========//
        Button btnPlaceSubmit = findViewById(R.id.btnEditPlace);
        Button btnPlaceDelete = findViewById(R.id.btnDeletePlace);

        PlaceIdUI = findViewById(R.id.PlaceIDEdit);
        PlaceNameUI = findViewById(R.id.PlaceNameEdit);
        PlaceHabitatUI = findViewById(R.id.PlaceCurrentEdit);
        NewHabitatUI = findViewById(R.id.food_stock);

        final Spinner staticSpinner1place = findViewById(R.id.static_spinner1place_edit);

        ArrayAdapter<CharSequence> staticAdapter1 = ArrayAdapter
                .createFromResource(this, R.array.animalHabitatCreate,
                        android.R.layout.simple_spinner_item);
        staticAdapter1
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner1place.setAdapter(staticAdapter1);

        //=======get data from intent=========//
        final String PlaceId = getIntent().getStringExtra("PlaceID");
        final String PlaceName = getIntent().getStringExtra("PlaceName");
        String PlaceHabitat = getIntent().getStringExtra("PlaceHabitat");

        //========set data to EditText views=======//
        PlaceIdUI.setText(PlaceId);
        PlaceNameUI.setText(PlaceName);
        PlaceHabitatUI.setText(PlaceHabitat);
        //========set data to EditText views=======//

        //=========Place Change Function===================//
        btnPlaceSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Place_Id = PlaceIdUI.getText().toString();
                final String PlaceNameChk = getIntent().getStringExtra("PlaceName");
                final String Place_Name = PlaceNameUI.getText().toString();
                final String Habitat = staticSpinner1place.getSelectedItem().toString();
                if (cm.getActiveNetworkInfo() != null) {
                    if (Place_Name.isEmpty()) {
                        PlaceNameUI.setError("Please Enter Place Name");
                        PlaceNameUI.requestFocus();
                    }else{
                        final DatabaseReference AnimCheck = FirebaseDatabase.getInstance().getReference().child("animal");
                        AnimCheck.orderByChild("place_name").equalTo(PlaceNameChk).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                        snapshot.getRef().child("place_name").setValue(Place_Name);
                                    }
                                    //============================ CALL SUBMIT FUNCTION ===================================================================//
                                    submitPlaceChg(new PlaceModel(Place_Id, Place_Name, Habitat));
                                    finish();
                                }else{
                                    //============================ CALL SUBMIT FUNCTION ===================================================================//
                                    submitPlaceChg(new PlaceModel(Place_Id, Place_Name, Habitat));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }else
                {
                    Toast.makeText(getApplicationContext(), "Internet Connection Is Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //===========Place Delete Function======================//
        btnPlaceDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Place_Id = PlaceIdUI.getText().toString();
                final String Place_Name = getIntent().getStringExtra("PlaceName");
                if (cm.getActiveNetworkInfo() != null) {
                    final DatabaseReference AnimCheck = FirebaseDatabase.getInstance().getReference().child("animal");
                    AnimCheck.orderByChild("place_name").equalTo(Place_Name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(getApplicationContext(), "Relocate All Animals Before Delete", Toast.LENGTH_SHORT).show();
                            }else{
                                //============================ CALL SUBMIT FUNCTION =====================================================//
                                DeletePlace();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(), "Internet Connection Is Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void submitPlaceChg (PlaceModel requests) {
        FirebaseDatabase.getInstance().getReference("place/" + Place_Id).setValue(requests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Place Data Changed!",Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DeletePlace() {
        FirebaseDatabase.getInstance().getReference().child("/place/"+Place_Id).removeValue().addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Place Deleted!",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
