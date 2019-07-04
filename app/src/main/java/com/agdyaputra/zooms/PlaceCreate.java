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
import com.google.firebase.database.FirebaseDatabase;

public class PlaceCreate extends AppCompatActivity {

    String Place_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_create);

        //======== Set Connectivity Manager ==========//
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //============================================//
        Button btnPlaceSubmit = findViewById(R.id.btnCreatePlace);
        final EditText PlaceNameUI = findViewById(R.id.PlaceNameCreate);
        final Spinner staticSpinner1place = findViewById(R.id.static_spinner1_place);

        ArrayAdapter<CharSequence> staticAdapter1 = ArrayAdapter
                .createFromResource(this, R.array.animalHabitatCreate,
                        android.R.layout.simple_spinner_item);
        staticAdapter1
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner1place.setAdapter(staticAdapter1);


        btnPlaceSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String PlaceName = PlaceNameUI.getText().toString();
                final String Habitat = staticSpinner1place.getSelectedItem().toString();
                if(PlaceName.isEmpty()){
                    PlaceNameUI.setError("Please enter Place Name");
                    PlaceNameUI.requestFocus();
                }else{
                    Place_Id = FirebaseDatabase.getInstance().getReference().push().getKey();
                    //============================ CALL SUBMIT FUNCTION ===================================================================//
                    submitPlace(new PlaceModel(Place_Id,PlaceName, Habitat));
                    finish();
                }
            }
        });
    }

    //============ SUBMIT FUNCTION =================================//
    private void submitPlace (PlaceModel requests) {
        FirebaseDatabase.getInstance().getReference("place/" + Place_Id).setValue(requests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Place_Id="";
                Toast.makeText(getApplicationContext(),"Place Added to Collection!",Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
