package com.example.cultivo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText etEmail, etPass, etPhone;
    Button btnRegister, btnGoogle , btnFacebook;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail = findViewById(R.id.EtEmail);
        etPass = findViewById(R.id.EtPass);
        etPhone = findViewById(R.id.EtPhone);
        btnRegister = findViewById(R.id.BtnSignUp);


        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    etEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    etPass.setError("Pass is Required");
                    return;
                }

                if (pass.length() < 6){
                    etPass.setError("Pass is Required");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);



            }
        });
    }
}