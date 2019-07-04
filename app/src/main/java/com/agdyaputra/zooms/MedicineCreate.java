package com.agdyaputra.zooms;

import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MedicineCreate extends AppCompatActivity {

    String Medicine_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_create);

        //======== Set Connectivity Manager ==========//
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //============================================//
        Button btnMedSubmit = findViewById(R.id.btnCreateMedicine);
        final EditText MedNameUI = findViewById(R.id.MedNameCreate);
        final EditText MedStockUI = findViewById(R.id.MedStockCreate);

        btnMedSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Med_Name = MedNameUI.getText().toString();
                final String Stock = MedStockUI.getText().toString();
                if (cm.getActiveNetworkInfo() != null) {
                    if (Med_Name.isEmpty()) {
                        MedNameUI.setError("Please enter Medicine Name");
                        MedNameUI.requestFocus();
                    } else if (Stock.isEmpty()) {
                        MedStockUI.setError("Please enter Initial Amounts");
                        MedStockUI.requestFocus();
                    } else {
                        Medicine_Id = FirebaseDatabase.getInstance().getReference().push().getKey();
                        final DatabaseReference MedNameCheck = FirebaseDatabase.getInstance().getReference().child("medicine");
                        MedNameCheck.orderByChild("med_name").equalTo(Med_Name).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Toast.makeText(getApplicationContext(), "Medicine Name Is Already Exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    //============================ CALL SUBMIT FUNCTION ===================================================================//
                                    submitMedicine(new MedicineModel(Medicine_Id, Med_Name, Stock));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Internet Connection Is Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //============ SUBMIT FUNCTION =================================//
    private void submitMedicine (MedicineModel requests) {
        FirebaseDatabase.getInstance().getReference("medicine/" + Medicine_Id).setValue(requests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Medicine_Id="";
                Toast.makeText(getApplicationContext(),"New Medicine Added!",Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
