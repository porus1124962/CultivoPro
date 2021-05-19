package com.example.cultivo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;


public class SignIn extends AppCompatActivity {
    EditText email,password;
    TextView gotoRegister;
    Button login;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPass);
        login = findViewById(R.id.btnLogin);
        gotoRegister = findViewById(R.id.gotoRegister);

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

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
                fAuth.signInWithEmailAndPassword(emailText,passwordText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignIn.this,"SignedIn successfully.", Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(authResult.getUser().getUid());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df =fStore.collection("User").document(uid);
        //extract data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());

                //identify the user access level
                if(documentSnapshot.getString("isSeller") != null){
                    //user is seller

                    startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
                    finish();
                }
                if (documentSnapshot.getString("isBuyer")!=null){
                    startActivity(new Intent(getApplicationContext(), userDashboard.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            DocumentReference df = FirebaseFirestore.getInstance().collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.getString("isSeller")!=null){
                        startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
                        finish();
                    }
                    if(documentSnapshot.getString("isBuyer")!=null){
                        startActivity(new Intent(getApplicationContext(),userDashboard.class));
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),SignIn.class));
                    finish();
                }
            });
        }
    }
}