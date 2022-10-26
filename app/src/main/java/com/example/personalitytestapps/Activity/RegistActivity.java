package com.example.personalitytestapps.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalitytestapps.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistActivity extends AppCompatActivity {

    // desc
    AutoCompleteTextView autoCompleteTextView;
    TextInputEditText et_email, et_username, et_npm, et_angkatan, et_umur, et_password;
    Button btn_regist;

    // variable
    String gender;

    // firebase
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        // init
        autoCompleteTextView = findViewById(R.id.autoComplete);
        et_email = findViewById(R.id.et_email);
        et_username = findViewById(R.id.et_username);
        et_npm = findViewById(R.id.et_npm);
        et_angkatan = findViewById(R.id.et_angkatan);
        et_umur = findViewById(R.id.et_umur);
        et_password = findViewById(R.id.et_password);
        btn_regist = findViewById(R.id.btn_regist);

        // firebase init
        auth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // button regist on click
        btn_regist.setOnClickListener(v -> {
            String email = et_email.getText().toString();
            String username = et_username.getText().toString();
            String npm = et_npm.getText().toString();
            String angkatan = et_angkatan.getText().toString();
            String umur = et_umur.getText().toString();
            String password = et_password.getText().toString();
            String getgender = gender;

            if (email.isEmpty()) {
                et_email.setError("Empty field!");
                et_email.requestFocus();
            } else if (username.isEmpty()) {
                et_username.setError("Empty field!");
                et_username.requestFocus();
            } else if (npm.isEmpty()) {
                et_npm.setError("Empty field!");
                et_npm.requestFocus();
            } else if (angkatan.isEmpty()) {
                et_angkatan.setError("Empty field!");
                et_angkatan.requestFocus();
            } else if (umur.isEmpty()) {
                et_umur.setError("Empty field!");
                et_umur.requestFocus();
            } else if (password.isEmpty()) {
                et_password.setError("Empty field!");
                et_password.requestFocus();
            } else if (npm.length() > 15) {
                et_npm.setError("NPM is not valid!!");
                et_npm.requestFocus();
            } else if (password.length() < 8) {
                et_password.setError("min 8 Char");
                et_password.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                et_email.setError("Email is not valid!");
                et_email.requestFocus();
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        user = auth.getCurrentUser();
                        user.sendEmailVerification();

                        HashMap<Object, String> map = new HashMap<>();
                        map.put("email", email);
                        map.put("username", username);
                        map.put("npm", npm);
                        map.put("angkatan", angkatan);
                        map.put("umur", umur);
                        map.put("gender", getgender);
                        map.put("role", "CalonAslab");
                        map.put("status", "belum mengerjakan");
                        map.put("sanguinis", "");
                        map.put("koleris", "");
                        map.put("melankolis", "");
                        map.put("plegmatis", "");

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("users");
                        reference.push().setValue(map);

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();

                        auth.signOut();

                        Toast.makeText(RegistActivity.this, "berhasil", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RegistActivity.this, "Failed Regist Account", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(RegistActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), WelcomePageActivity.class);
        startActivity(intent);
        finish();
    }
}