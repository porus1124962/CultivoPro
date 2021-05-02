package com.example.cultivo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SignIn extends AppCompatActivity {
    EditText email,password;
    TextView gotoRegister;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPass);
        login = findViewById(R.id.btnLogin);
        gotoRegister = findViewById(R.id.gotoRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();

                if (TextUtils.isEmpty(emailText)){
                    email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(passwordText)){
                    password.setError("Email is Required");
                    return;
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}