package com.example.cultivo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class userDashboard extends AppCompatActivity {
    Button btnLogOut,btnAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
//        btnLogOut = findViewById(R.id.btnLogOut);
//        btnAccount = findViewById(R.id.accountBtn);

        //bottomNavigation code start
        //initialize and asign var
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set Dashboard selected
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        //perform itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        return true;

                    case R.id.categories:
                        startActivity(new Intent(getApplicationContext(),Category.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bids:
                        startActivity(new Intent(getApplicationContext(),Bids.class));
                        overridePendingTransition(0,0);
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


//        btnAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Account.class));
//                finish();
//            }
//        });
//
//        btnLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(),SignIn.class));
//                finish();
//            }
//        });
    }
}