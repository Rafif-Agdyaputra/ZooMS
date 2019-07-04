package com.agdyaputra.zooms;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MedicineEdit extends AppCompatActivity {

    EditText MedIdUI, MedNameUI, MedStockUI, MStockUI;
    String Medicine_Id;
    String Anim_name, Gender, Place_name,Species;
    String Treatment_Id;
    Object item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_edit);

        //======== Set Connectivity Manager//
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //=========//
        Button btnMedAdd = findViewById(R.id.btnMedAdd);
        Button btnMedTake = findViewById(R.id.btnMedTake);
        Button btnMedDel = findViewById(R.id.btnDeleteMed);

        MedIdUI = findViewById(R.id.MedIdEdit);
        MedNameUI = findViewById(R.id.MedNameEdit);
        MedStockUI = findViewById(R.id.med_current_stock);
        MStockUI = findViewById(R.id.med_stock);
        final AutoCompleteTextView SpinnerMedEdit = findViewById(R.id.SickAnim);

        //====== POPULATE AUTOCOMPLETE-TEXTVIEW WITH DATA ============//
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        //Create a new ArrayAdapter with your context and the simple layout for the dropdown menu provided by Android
        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        database.child("animal").orderByChild("status").equalTo("Sick").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                autoComplete.clear();
                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("animal_name").getValue(String.class);
                    autoComplete.add(suggestion);
                    autoComplete.registerDataSetObserver(new DataSetObserver() {
                        @Override
                        public void onChanged() {
                            super.onChanged();
                            Log.d("test", "dataset changed");

                                item = autoComplete.getItem(0);

                                Log.d("test", "item.toString " + item.toString());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        //========== DECLARE AUTOCOMPLETE TEXTVIEW. AND ADDING ADAPTER =============//
        SpinnerMedEdit.setAdapter(autoComplete);

        SpinnerMedEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerMedEdit.showDropDown();
            }
        });

        //====== POPULATE AUTOCOMPLETE-TEXTVIEW WITH DATA ============//

        //=======get data from intent=========//
        final String MedId = getIntent().getStringExtra("MedID");
        final String MedName = getIntent().getStringExtra("MedName");
        final String MedStock = getIntent().getStringExtra("MedStock");

        //========set data to EditText views=======//
        MedIdUI.setText(MedId);
        MedNameUI.setText(MedName);
        MedStockUI.setText(MedStock);
        //========set data to EditText views=======//

        //========= Add Function ========//
        btnMedAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medicine_Id = MedIdUI.getText().toString();
                final String Med_Name = MedNameUI.getText().toString();
                if (cm.getActiveNetworkInfo() != null) {
                    if (Med_Name.isEmpty()) {
                        MedNameUI.setError("Please Enter Medicine Name");
                        MedNameUI.requestFocus();
                    } else if (MStockUI.getText().toString().isEmpty()) {
                        MStockUI.setError("Please Enter Stock Values");
                        MStockUI.requestFocus();
                    } else {
                        final int MedCurrStock = Integer.parseInt(MedStockUI.getText().toString());
                        final int Stock = Integer.parseInt(MStockUI.getText().toString());
                        final String result = String.valueOf(MedCurrStock + Stock);
                        if (Stock <= 0) {
                            MStockUI.setError("Stock Cannot be lower than 1");
                            MStockUI.requestFocus();
                        } else {
                            //============================ CALL SUBMIT FUNCTION ===================================================================//
                            submitAddMed(new MedicineModel(Medicine_Id, Med_Name, result));
                            finish();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Internet Connection Is Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //========= Take Function ========//
        btnMedTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medicine_Id = MedIdUI.getText().toString();
                final String Med_Name = MedNameUI.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy '@' HH:mm:ss ");
                final String currentDateandTime = sdf.format(new Date());

                if (cm.getActiveNetworkInfo() != null) {
                    if(Med_Name.isEmpty()){
                        MedNameUI.setError("Please Enter Medicine Name");
                        MedNameUI.requestFocus();
                    }
                    else if (MStockUI.getText().toString().isEmpty()){
                        MStockUI.setError("Please Enter Stock Values");
                        MStockUI.requestFocus();
                    }
                    else if (autoComplete.getCount()==0){
                        SpinnerMedEdit.setError("No Sick Animals");
                        SpinnerMedEdit.requestFocus();
                    }
                    else {
                        final int MedCurrStock = Integer.parseInt(MedStockUI.getText().toString());
                        final int Stock = Integer.parseInt(MStockUI.getText().toString());
                        final String result = String.valueOf(MedCurrStock - Stock);
                        if (Stock <= 0) {
                            MStockUI.setError("Stock Cannot be lower than 1");
                            MStockUI.requestFocus();
                        } else if(Integer.parseInt(result)<=0){
                            MStockUI.setError("Current stock cannot be lower than 0");
                            MStockUI.requestFocus();
                        }
                        else {
                            final DatabaseReference AnimnameCheck = FirebaseDatabase.getInstance().getReference().child("animal");
                            AnimnameCheck.orderByChild("animal_name").equalTo(item.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()){
                                        Anim_name = suggestionSnapshot.child("animal_name").getValue(String.class);
                                        Gender = suggestionSnapshot.child("gender").getValue(String.class);
                                        Place_name = suggestionSnapshot.child("place_name").getValue(String.class);
                                        Species = suggestionSnapshot.child("species").getValue(String.class);
                                        Treatment_Id = FirebaseDatabase.getInstance().getReference().push().getKey();
                                        //============================ CALL SUBMIT FUNCTION ===================================================================//
                                        submitTakeMed(new MedicineModel(Medicine_Id, Med_Name, result));
                                        submitTreatment(new TreatmentModel(Med_Name,String.valueOf(Stock),Treatment_Id,Anim_name,Species,Gender,Place_name,currentDateandTime));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });




                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Internet Connection Is Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMedDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medicine_Id = MedIdUI.getText().toString();
                DeleteMed();
                finish();
            }
        });
    }

    //============ SUBMIT FUNCTION =================================//
    private void submitAddMed (MedicineModel requests) {
        FirebaseDatabase.getInstance().getReference("medicine/" + Medicine_Id).setValue(requests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Medicine Data added!",Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitTakeMed (MedicineModel requests) {
        FirebaseDatabase.getInstance().getReference("medicine/" + Medicine_Id).setValue(requests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Treatment Applied, Medicine Data Taken!",Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitTreatment (TreatmentModel requests) {
        FirebaseDatabase.getInstance().getReference("treatment/" + Treatment_Id).setValue(requests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }

        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DeleteMed() {
        FirebaseDatabase.getInstance().getReference().child("/medicine/" + Medicine_Id).removeValue().addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Medicine Deleted!",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
