package com.example.personalitytestapps.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.personalitytestapps.MainActivity;
import com.example.personalitytestapps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    // define
    EditText et_email,et_password;
    Button btn_login;
    ProgressDialog dialog;

    // define firebase
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // init
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.create();

        // init firebase
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btn_login.setOnClickListener(v -> {
            dialog.show();
            String email = et_email.getText().toString();
            String password = et_password.getText().toString();

            if (email.isEmpty()){
                dialog.dismiss();
                et_email.setError("Empty Field!");
                et_email.requestFocus();
            }
            else if (password.isEmpty()){
                dialog.dismiss();
                et_password.setError("Empty Field!");
                et_password.requestFocus();
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                dialog.dismiss();
                et_email.setError("Not Valid!");
                et_email.requestFocus();
            }
            else if (password.length() < 8){
                dialog.dismiss();
                et_password.setError("Min 8 Char");
                et_password.requestFocus();
            }
            else {
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        user = Objects.requireNonNull(task.getResult()).getUser();
                        if (user != null ){
                            if (user.isEmailVerified()){
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(), "Berhaisl Login", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                auth.signOut();
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Akun anda belum terverifikasi", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to login", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



    public void to_regist(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),WelcomePageActivity.class);
        startActivity(intent);
        finish();
    }
}