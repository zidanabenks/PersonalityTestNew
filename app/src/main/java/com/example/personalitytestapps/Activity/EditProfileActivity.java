package com.example.personalitytestapps.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personalitytestapps.MainActivity;
import com.example.personalitytestapps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    EditText et_email,et_username,et_npm,et_angkatan,et_umur;
    Button btn_simpan;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        et_email = findViewById(R.id.et_email);
        et_username = findViewById(R.id.et_username);
        et_npm = findViewById(R.id.et_npm);
        et_angkatan = findViewById(R.id.et_angkatan);
        et_umur = findViewById(R.id.et_umur);
        btn_simpan  = findViewById(R.id.btn_simpan);

        // Data Snapshot, get data user from database
        Query q = reference.orderByChild("email").equalTo(user.getEmail());
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String email = "" + ds.child("email").getValue();
                    String username = "" + ds.child("username").getValue();
                    String npm = "" + ds.child("npm").getValue();
                    String angkatan = "" + ds.child("angkatan").getValue();
                    String umur = "" + ds.child("umur").getValue();

                    et_email.setText(email);
                    et_username.setText(username);
                    et_npm.setText(npm);
                    et_angkatan.setText(angkatan);
                    et_umur.setText(umur);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_simpan.setOnClickListener(v -> {
            String username = et_username.getText().toString();
            String npm = et_npm.getText().toString();
            String angkatan = et_angkatan.getText().toString();
            String umur = et_umur.getText().toString();

            if (username.isEmpty()){
                et_username.setError("Empty field!");
                et_username.requestFocus();
            }
            else if (npm.isEmpty()){
                et_npm.setError("Empty field!");
                et_npm.requestFocus();
            }
            else if (angkatan.isEmpty()){
                et_angkatan.setError("Empty field!");
                et_angkatan.requestFocus();
            }
            else if (umur.isEmpty()){
                et_umur.setError("Empty field!");
                et_umur.requestFocus();
            }
            else if (npm.length() > 15){
                et_npm.setError("NPM is not valid!!");
                et_npm.requestFocus();
            }else{
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("username",username);
                hashMap.put("npm",npm);
                hashMap.put("angkatan",angkatan);
                hashMap.put("umur",umur);

                reference.orderByChild("email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            ds.getRef().updateChildren(hashMap);
                            Toast.makeText(EditProfileActivity.this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}