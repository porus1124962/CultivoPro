package com.example.cultivo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class Account extends AppCompatActivity {
    TextView txtNewUserName, txtNewEmail, txtNewPass, txtNewPhone,txtNewLocation;
    EditText etNewUserName, etNewEmail, etNewPass, etNewPhone,etNewLocation;
    Button btnResetData,btnLogoutAccJs;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        txtNewUserName = findViewById(R.id.TxtUsernamePrev);
        txtNewEmail = findViewById(R.id.TxtEmailPrev);
        txtNewPass = findViewById(R.id.TxtPasswordPrev);
        txtNewPhone = findViewById(R.id.TxtPhonePrev);
        txtNewLocation = findViewById(R.id.TxtLocationPrev);

        etNewUserName =findViewById(R.id.etNewUsername);
        etNewEmail =findViewById(R.id.etNewEmail);
        etNewPass =findViewById(R.id.etNewPassword);
        etNewPhone =findViewById(R.id.etNewPhone);
        etNewLocation =findViewById(R.id.etNewLocation);

        btnResetData = findViewById(R.id.resetProfile);
        btnLogoutAccJs = findViewById(R.id.btnLogoutAcc);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        //firebase access
        String userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        //fetch fields in textView
        DocumentReference documentReference = fStore.collection("User").document(userId);
        documentReference.addSnapshotListener(this, (documentSnapshot, e) -> {
          if (documentSnapshot.exists()){
              txtNewUserName.setText(documentSnapshot.getString("Name"));
              txtNewEmail.setText(documentSnapshot.getString("Email"));
              txtNewPass.setText(documentSnapshot.getString("Password"));
              txtNewPhone.setText(documentSnapshot.getString("Phone"));
              txtNewLocation.setText(documentSnapshot.getString("Address"));
          }else{
              Toast.makeText(Account.this, e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });

        //reset account data
        btnResetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNewUserName.getText().toString().isEmpty() || etNewEmail.getText().toString().isEmpty() || etNewPass.getText().toString().isEmpty() || etNewPhone.getText().toString().isEmpty() || etNewLocation.getText().toString().isEmpty() ){
                    Toast.makeText(Account.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String newEmail = etNewEmail.getText().toString();
                user.updateEmail(newEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("User").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("Name",etNewUserName.getText().toString());
                        edited.put("Email",newEmail);
                        edited.put("Password",etNewPass.getText().toString());
                        edited.put("Phone",etNewPhone.getText().toString());
                        edited.put("Address",etNewLocation.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Account.this, "info Changed", Toast.LENGTH_SHORT).show();
                                //FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(),SignIn.class));
                                finish();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Account.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnLogoutAccJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),SignIn.class));
                finish();
            }
        });

        //bottomNavigation code start
        //initialize and asign var
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set Dashboard selected
        bottomNavigationView.setSelectedItemId(R.id.account);

        //perform itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        DocumentReference df =fStore.collection("User").document(user.getUid());
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
                        return true;

                    case R.id.categories:
                        startActivity(new Intent(getApplicationContext(),Category.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.schemes:
                        startActivity(new Intent(getApplicationContext(),Schemes.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.account:
                        return true;
                }
                return false;
            }
        });
        //bottomNavigation code end

    }
}