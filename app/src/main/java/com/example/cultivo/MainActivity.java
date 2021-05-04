package com.example.cultivo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText etEmail, etPass, etPhone,etName,etWarehouse;
    Button btnRegister, btnGoogle , btnFacebook;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    TextView goToSignIn;
    RadioButton rbBtnSeller,rbBtnBuyer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbBtnSeller = findViewById(R.id.rbSeller);
        rbBtnBuyer = findViewById(R.id.rbBuyer);
        etEmail = findViewById(R.id.EtEmail);
        etPass = findViewById(R.id.EtPass);
        etPhone = findViewById(R.id.EtPhone);
        etName = findViewById(R.id.etName);
        etWarehouse = findViewById(R.id.wareHouse);
        btnRegister = findViewById(R.id.BtnSignUp);
        goToSignIn = findViewById(R.id.goToSignIn);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);



        //redirect to signin
        goToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignIn.class));
                finish();
            }
        });

        //Start User registration
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), SignIn.class));
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String address = etWarehouse.getText().toString().trim();
                //checkbox validation
                if(!(rbBtnSeller.isChecked() || rbBtnBuyer.isChecked())) {
                    Toast.makeText(MainActivity.this, "select account type", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name)){
                    etName.setError("Email is Required");
                    return;
                }
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
                if (TextUtils.isEmpty(phone)){
                    etPhone.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(address)){
                    etWarehouse.setError("Email is Required");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

              /*  fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), SignIn.class));
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
                fAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = fAuth.getCurrentUser();
                        Toast.makeText(MainActivity.this,"Account Created", Toast.LENGTH_SHORT).show();
                        DocumentReference df = fStore.collection("User").document(user.getUid());
                        Map<String,Object> userInfo = new HashMap<>();
                        userInfo.put("Name",name);
                        userInfo.put("Email",email);
                        userInfo.put("Password",pass);
                        userInfo.put("Phone",phone);
                        userInfo.put("Address",address);

                        //specify if the user is admin
                        if(rbBtnSeller.isChecked()){
                            userInfo.put("isSeller","1");
                        }
                        if(rbBtnBuyer.isChecked()){
                            userInfo.put("isBuyer","1");
                        }
                        df.set(userInfo);

                        if(rbBtnSeller.isChecked()){
                            startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
                            finish();
                        }
                        if(rbBtnBuyer.isChecked()){
                            startActivity(new Intent(getApplicationContext(),userDashboard.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"failed to Created", Toast.LENGTH_SHORT).show();
                    }
                });





            }

        });
    }
}