package com.example.personalitytestapps.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.personalitytestapps.R;

public class SplashScreenctivity extends AppCompatActivity {

    int time = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screenctivity);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), WelcomePageActivity.class);
            startActivity(intent);
            finish();
        },time);

    }
}