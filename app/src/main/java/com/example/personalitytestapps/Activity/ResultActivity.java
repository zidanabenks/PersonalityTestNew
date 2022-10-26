package com.example.personalitytestapps.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.personalitytestapps.MainActivity;
import com.example.personalitytestapps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultActivity extends AppCompatActivity {

    Button btn_presentase;

    TextView tv_tipe,tv_referensi,tv_kelebihan;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    int s = 0;
    int k = 0;
    int m = 0;
    int p = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        tv_tipe = findViewById(R.id.tv_tipe);
        tv_referensi = findViewById(R.id.tv_referensi);
        tv_kelebihan = findViewById(R.id.tv_kelebihan);

        reference.orderByChild("email").equalTo(user.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String getSanguinis = "" + ds.child("sanguinis").getValue();
                    String getKoleris = "" + ds.child("koleris").getValue();
                    String getMelankolis = "" + ds.child("melankolis").getValue();
                    String getPlegmatis = "" + ds.child("plegmatis").getValue();

                    s = Integer.parseInt(getSanguinis) * 100;
                    k = Integer.parseInt(getKoleris) * 100;
                    m = Integer.parseInt(getMelankolis) * 100;
                    p = Integer.parseInt(getPlegmatis) * 100;

                    if (s > k & s > m & s > p){

                        tv_tipe.setText(R.string.s_tipe);
                        tv_referensi.setText(R.string.s_referensi);
                        tv_kelebihan.setText(R.string.s_kelebihan);

                    }
                    else if (k > s & k > m & k > p){

                        tv_tipe.setText(R.string.k_tipe);
                        tv_referensi.setText(R.string.k_referensi);
                        tv_kelebihan.setText(R.string.k_kelebihan);

                    }
                    else if (m > s & m > k & m > p){

                        tv_tipe.setText(R.string.m_tipe);
                        tv_referensi.setText(R.string.m_referensi);
                        tv_kelebihan.setText(R.string.m_kelebihan);

                    }
                    else if (p > s & p > k & p > m){

                        tv_tipe.setText(R.string.p_tipe);
                        tv_referensi.setText(R.string.p_referensi);
                        tv_kelebihan.setText(R.string.p_kelebihan);

                    }

                    else if (s == k || k == s){
                        tv_tipe.setText(R.string.s_k);
                    }
                    else if (s == m || m == s){
                        tv_tipe.setText(R.string.s_m);
                    }
                    else if (s == p || p == s){
                        tv_tipe.setText(R.string.s_p);
                    }
                    else if (k == m || m == k){
                        tv_tipe.setText(R.string.k_m);
                    }
                    else if (k == p || p == k){
                        tv_tipe.setText(R.string.k_p);
                    }
                    else if (m == p || p == m){
                        tv_tipe.setText(R.string.m_p);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_presentase = findViewById(R.id.btn_presentase);
        btn_presentase.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
            startActivity(intent);
            finish();
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