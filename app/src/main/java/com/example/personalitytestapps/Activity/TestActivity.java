package com.example.personalitytestapps.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.personalitytestapps.MainActivity;
import com.example.personalitytestapps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TestActivity extends AppCompatActivity {

    // Material checkbox
    MaterialCheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6,checkBox7,checkBox8,checkBox9,checkBox10,
            checkBox11,checkBox12,checkBox13,checkBox14,checkBox15,checkBox16,checkBox17,checkBox18,checkBox19,checkBox20,
    checkBox21,checkBox22,checkBox23,checkBox24,checkBox25,checkBox26;
    Button btn_submit;

    RadioGroup radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5,radioGroup6,radioGroup7,radioGroup8,radioGroup9,radioGroup10;

    int sanguinis = 0;
    int koleris = 0;
    int melankolis = 0;
    int plegmatis = 0;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        radioGroup1 = findViewById(R.id.rg_1);
        radioGroup2 = findViewById(R.id.rg_2);
        radioGroup3 = findViewById(R.id.rg_3);
        radioGroup4 = findViewById(R.id.rg_4);
        radioGroup5 = findViewById(R.id.rg_5);
        radioGroup6 = findViewById(R.id.rg_6);
        radioGroup7 = findViewById(R.id.rg_7);
        radioGroup8 = findViewById(R.id.rg_8);
        radioGroup9 = findViewById(R.id.rg_9);
        radioGroup10 = findViewById(R.id.rg_10);

        checkBox1 = findViewById(R.id.ch_1);
        checkBox2 = findViewById(R.id.ch_2);
        checkBox3 = findViewById(R.id.ch_3);
        checkBox4 = findViewById(R.id.ch_4);
        checkBox5 = findViewById(R.id.ch_5);
        checkBox6 = findViewById(R.id.ch_6);
        checkBox7 = findViewById(R.id.ch_7);
        checkBox8 = findViewById(R.id.ch_8);
        checkBox9 = findViewById(R.id.ch_9);
        checkBox10 = findViewById(R.id.ch_10);

        checkBox11 = findViewById(R.id.ch_11);
        checkBox12 = findViewById(R.id.ch_12);
        checkBox13 = findViewById(R.id.ch_13);
        checkBox14 = findViewById(R.id.ch_14);
        checkBox15 = findViewById(R.id.ch_15);
        checkBox16 = findViewById(R.id.ch_16);
        checkBox17 = findViewById(R.id.ch_17);
        checkBox18 = findViewById(R.id.ch_18);
        checkBox19 = findViewById(R.id.ch_19);
        checkBox20 = findViewById(R.id.ch_20);

        checkBox21 = findViewById(R.id.ch_21);
        checkBox22 = findViewById(R.id.ch_22);
        checkBox23 = findViewById(R.id.ch_23);
        checkBox24 = findViewById(R.id.ch_24);
        checkBox25 = findViewById(R.id.ch_25);
        checkBox26 = findViewById(R.id.ch_26);

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(v -> {

            if (checkBox1.isChecked()){
                sanguinis++;
            }
            if (checkBox5.isChecked()){
                sanguinis++;
            }
            if (checkBox9.isChecked()){
                sanguinis++;
            }
            if (checkBox13.isChecked()){
                sanguinis++;
            }
            if (checkBox17.isChecked()){
                sanguinis++;
            }
            if (checkBox21.isChecked()){
                sanguinis++;
            }
            if (checkBox25.isChecked()){
                sanguinis++;
            }

            if (checkBox2.isChecked()){
                koleris++;
            }
            if (checkBox6.isChecked()){
                koleris++;
            }
            if (checkBox10.isChecked()){
                koleris++;
            }
            if (checkBox14.isChecked()){
                koleris++;
            }
            if (checkBox18.isChecked()){
                koleris++;
            }
            if (checkBox22.isChecked()){
                koleris++;
            }
            if (checkBox26.isChecked()){
                koleris++;
            }

            if (checkBox3.isChecked()){
                melankolis++;
            }
            if (checkBox7.isChecked()){
                melankolis++;
            }
            if (checkBox11.isChecked()){
                melankolis++;
            }
            if (checkBox15.isChecked()){
                melankolis++;
            }
            if (checkBox19.isChecked()){
                melankolis++;
            }
            if (checkBox23.isChecked()){
                melankolis++;
            }

            if (checkBox4.isChecked()){
                plegmatis++;
            }
            if (checkBox8.isChecked()){
                plegmatis++;
            }
            if (checkBox12.isChecked()){
                plegmatis++;
            }
            if (checkBox16.isChecked()){
                plegmatis++;
            }
            if (checkBox20.isChecked()){
                plegmatis++;
            }
            if (checkBox24.isChecked()){
                plegmatis++;
            }

            // radio button
            int id1 = radioGroup1.getCheckedRadioButtonId();
            if (id1==R.id.rd_1){
                sanguinis++;
            }else if (id1==R.id.rd_2){
                koleris++;
            }

            int id2 = radioGroup2.getCheckedRadioButtonId();
            if (id2==R.id.rd_3){
                sanguinis++;
            }else if (id2==R.id.rd_4){
                melankolis++;
            }

            int id3 = radioGroup3.getCheckedRadioButtonId();
            if (id3==R.id.rd_5){
                sanguinis++;
            }else if (id3==R.id.rd_6){
                plegmatis++;
            }

            int id4 = radioGroup4.getCheckedRadioButtonId();
            if (id4==R.id.rd_7){
                koleris++;
            }else if (id4==R.id.rd_8){
                melankolis++;
            }

            int id5 = radioGroup5.getCheckedRadioButtonId();
            if (id5==R.id.rd_9){
                koleris++;
            }else if (id5==R.id.rd_10){
                plegmatis++;
            }

            int id6 = radioGroup6.getCheckedRadioButtonId();
            if (id6==R.id.rd_11){
                melankolis++;
            }else if (id6==R.id.rd_12){
                plegmatis++;
            }

            int id7 = radioGroup7.getCheckedRadioButtonId();
            if (id7==R.id.rd_13){
                melankolis++;
            }
            else if (id7 == R.id.rd_14){
                plegmatis++;
            }
            else if (id7 == R.id.rd_15){
                koleris++;
            }
            else if (id7 == R.id.rd_16){
                sanguinis++;
            }

            int id8 = radioGroup8.getCheckedRadioButtonId();
            if (id8==R.id.rd_17){
                melankolis++;
            }
            else if (id8 == R.id.rd_18){
                sanguinis++;
            }
            else if (id8 == R.id.rd_19){
                plegmatis++;
            }
            else if (id8 == R.id.rd_20){
                koleris++;
            }

            int id9 = radioGroup9.getCheckedRadioButtonId();
            if (id9==R.id.rd_21){
                melankolis++;
            }
            else if (id9 == R.id.rd_22){
                plegmatis++;
            }
            else if (id9 == R.id.rd_23){
                sanguinis++;
            }
            else if (id9 == R.id.rd_24){
                koleris++;
            }

            int id10 = radioGroup10.getCheckedRadioButtonId();
            if (id10==R.id.rd_25){
                sanguinis++;
            }
            else if (id10 == R.id.rd_26){
                melankolis++;
            }
            else if (id10 == R.id.rd_27){
                koleris++;
            }
            else if (id10 == R.id.rd_28){
                plegmatis++;
            }

            HashMap<String,Object> hasmap = new HashMap<>();
            hasmap.put("sanguinis",Integer.toString(sanguinis));
            hasmap.put("koleris",Integer.toString(koleris));
            hasmap.put("melankolis",Integer.toString(melankolis));
            hasmap.put("plegmatis",Integer.toString(plegmatis));
            hasmap.put("status","sudah mengerjakan");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("users");
            reference.orderByChild("email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()){
                        ds.getRef().updateChildren(hasmap);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
            startActivity(intent);
            finish();

            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}