package com.example.personalitytestapps.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.personalitytestapps.R;

public class AboutActivity extends AppCompatActivity {

    TextView txt_devname, txt_androidversion;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        txt_devname = findViewById(R.id.txt_devname);
        txt_androidversion = findViewById(R.id.txt_androidversion);

        txt_devname.setText("Muhammad Rizki Zidan");
        txt_androidversion.setText("Versi : v.1.0");

    }
}