package com.example.cultivo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends AppCompatActivity {

    RecyclerView pRecyclerView;
    List<ProductData> myProductList;
    ProductData pProductData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        pRecyclerView = (RecyclerView)findViewById(R.id.RecyclerViewCard);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AdminDashboard.this,1);
        pRecyclerView.setLayoutManager(gridLayoutManager);
        myProductList= new ArrayList<>();
        pProductData = new ProductData("Wheat","Wheat is a grass widely cultivated for its seed, a cereal grain which is a worldwide staple food.","1500","1-12-12","1-13-12","uid",R.drawable.wheat);
        myProductList.add(pProductData);
        pProductData = new ProductData("Sugarcan","Wheat is a grass widely cultivated for its seed, a cereal grain which is a worldwide staple food.","7500","1-12-12","1-13-12","uid",R.drawable.sugarcan);
        myProductList.add(pProductData);
        pProductData = new ProductData("Rice","Wheat is a grass widely cultivated for its seed, a cereal grain which is a worldwide staple food.","4000","1-12-12","1-13-12","uid",R.drawable.rice);
        myProductList.add(pProductData);


        ProductAdapter productAdapter = new ProductAdapter(AdminDashboard.this,myProductList);
        pRecyclerView.setAdapter(productAdapter);





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
//        btLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(),SignIn.class));
//                finish();
//            }
//        });



    }
}