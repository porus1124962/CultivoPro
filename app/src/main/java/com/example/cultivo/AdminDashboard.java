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

public class AdminDashboard extends AppCompatActivity {
    Button btLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btLogOut = findViewById(R.id.btnLogoutAdmin);
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
                    case R.id.product:
                        startActivity(new Intent(getApplicationContext(),Product.class));
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
        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),SignIn.class));
                finish();
            }
        });
    }
}