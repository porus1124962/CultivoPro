package com.example.cultivo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Category extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        //bottomNavigation code start
        //initialize and asign var
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set Dashboard selected
        bottomNavigationView.setSelectedItemId(R.id.categories);

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

                        return true;
                    case R.id.schemes:
                        startActivity(new Intent(getApplicationContext(),Schemes.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),Account.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        //bottomNavigation code end
    }
}