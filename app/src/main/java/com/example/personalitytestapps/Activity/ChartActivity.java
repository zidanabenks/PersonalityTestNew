package com.example.personalitytestapps.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.personalitytestapps.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    private PieChart pieChart;

    int s = 0;
    int k = 0;
    int m = 0;
    int p = 0;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        pieChart = findViewById(R.id.chart);

        pieChart = findViewById(R.id.chart);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

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

                    setUpChart();
                    loadChart();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setUpChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("By PersonalityTest");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);

    }

    private void loadChart(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(s,"Sanguinis"));
        entries.add(new PieEntry(k,"Koleris"));
        entries.add(new PieEntry(m,"Melankolis"));
        entries.add(new PieEntry(p,"Plegmatis"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }
        for (int color: ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries,"Personality");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        dataSet.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =  new Intent(getApplicationContext(),ResultActivity.class);
        startActivity(intent);
        finish();
    }

    public void back(View view) {
        onBackPressed();
    }
}