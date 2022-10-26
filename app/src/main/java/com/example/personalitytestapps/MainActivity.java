package com.example.personalitytestapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalitytestapps.Activity.AboutActivity;
import com.example.personalitytestapps.Activity.EditProfileActivity;
import com.example.personalitytestapps.Activity.LoginActivity;
import com.example.personalitytestapps.Activity.ResultActivity;
import com.example.personalitytestapps.Activity.TestActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn_test;
    AlertDialog.Builder alertDialog;
    TextView tv_username,tv_status;

    // firebase
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    AlertDialog.Builder alert, logout_alert;

    ImageView more,img_status;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        more = findViewById(R.id.more);
        img_status = findViewById(R.id.img_status);
        tv_username = findViewById(R.id.tv_username);
        tv_status = findViewById(R.id.status_text);

        more.setOnClickListener(v -> {
            showOptions();
        });

        Query query = reference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String username = "" + ds.child("username").getValue();
                    type = "" + ds.child("status").getValue();

                    tv_username.setText(username);

                    if (type.equals("belum mengerjakan")){
                        img_status.setImageResource(R.drawable.ic_baseline_error_24);
                        tv_status.setText("Anda belum mengerjakan test");
                        btn_test.setText("Kerjakan Test");
                    }else{
                        img_status.setImageResource(R.drawable.ic_baseline_check_circle_24);
                        tv_status.setText("Anda sudah mengerjakan test");
                        btn_test.setText("Lihat hasil");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_test = findViewById(R.id.btn_test);

        // alert options
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Ingin kerjakan test sekarang?");
        alertDialog.setMessage("Perhatian : anda hanya dapat melakukan test satu kali");
        alertDialog.setCancelable(true);
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(getApplicationContext(), TestActivity.class);
            startActivity(intent);
            finish();
        });
        alertDialog.create();

        // alert for logout

        logout_alert = new AlertDialog.Builder(this);
        logout_alert.setTitle("Logout");
        logout_alert.setMessage("Anda yakin ingin logout?");
        logout_alert.setCancelable(true);
        logout_alert.setPositiveButton("Ya", (dialog, which) -> {
            auth.signOut();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        });
        logout_alert.setNegativeButton("Tidak", (dialog, which) -> {
            dialog.dismiss();
        });
        logout_alert.create();

        // button test on click action
        btn_test.setOnClickListener(v -> {
            if (btn_test.getText().toString().equals("Kerjakan Test")){
                alertDialog.show();
            }
            else {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
 
    private void showOptions() {

        final String[] option = {"Edit Profile","Developer","Logout"};

        alert = new AlertDialog.Builder(this);
        alert.setTitle("Options");
        alert.setItems(option, (dialog, which) -> {
            if ("Logout".equals(option[which])){
                logout_alert.show();
            }
            if ("Developer".equals(option[which])){
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                finish();
            }
            else if ("Edit Profile".equals(option[which])){
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alert.create();
        alert.show();

    }

}