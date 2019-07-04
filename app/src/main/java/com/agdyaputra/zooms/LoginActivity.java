package com.agdyaputra.zooms;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button Login;
    EditText PasswordUI, UserNameUI;
    String Department;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Department = "";
        //======== Set Connectivity Manager//
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //=========//

        PasswordUI = findViewById(R.id.password);
        UserNameUI = findViewById(R.id.username);

        Login = findViewById(R.id.signin);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = UserNameUI.getText().toString();
                final String Password = PasswordUI.getText().toString();

                if (cm.getActiveNetworkInfo() != null) {
                    if(Username.isEmpty()){
                        UserNameUI.setError("Please Fill Username");
                        UserNameUI.requestFocus();
                    } else if (Password.isEmpty()){
                        PasswordUI.setError("Please Fill Password");
                        PasswordUI.requestFocus();
                    }
                    else {
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("employee");
                        database.orderByChild("username").equalTo(Username).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                                        String password = suggestionSnapshot.child("password").getValue(String.class);
                                        String First = suggestionSnapshot.child("first_name").getValue(String.class);
                                        String Last = suggestionSnapshot.child("last_name").getValue(String.class);
                                        Department = suggestionSnapshot.child("dept_name").getValue(String.class);
                                        if (password.equals(Password)) {
                                            Toast.makeText(getApplicationContext(), First+" "+Last+" "+"Welcome To ZooMS!", Toast.LENGTH_SHORT).show();
                                            AuthUser();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Invalid Username And Password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Invalid Username And Password", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Internet Connection Is Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AuthUser(){
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("Dept_Name", Department);
        startActivity(intent);
    }


}
