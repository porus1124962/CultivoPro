package com.example.cultivo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

import io.grpc.Context;

public class uploadProduct extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    ImageView productImage;
    EditText farmerNameEt, priceET, feildET,qualityEt,startTimeEt,endTimeET,descriptionEt;
    Uri uri;
    String ImageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        productImage = findViewById(R.id.ivUpload_productImage);
        farmerNameEt= findViewById(R.id.etFarmer);
        priceET= findViewById(R.id.etPrice);
        feildET= findViewById(R.id.etFeild);
        qualityEt= findViewById(R.id.etQuality);
        startTimeEt= findViewById(R.id.etStartTime);
        endTimeET= findViewById(R.id.etEndTime);
        descriptionEt= findViewById(R.id.etDescription);


        //bottomNavigation code start
        //initialize and asign var
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set Dashboard selected
        bottomNavigationView.setSelectedItemId(R.id.product);

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
                    case R.id.product:
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

    public void btnSelectImage(View view) {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== RESULT_OK){

            uri = data.getData();
            productImage.setImageURI(uri);
        }else{
            Toast.makeText(this,"you have not picked image", Toast.LENGTH_SHORT).show();
        }
    }


    public void uploadProductFunc(){
        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference().child("ProductImage").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                ImageUrl = urlImage.toString();
                uploadProductDetail();

                Toast.makeText(uploadProduct.this,"Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void uploadProductBtn(View view) {
        uploadProductFunc();
    }

    public void uploadProductDetail(){
        ProductData productData = new ProductData(
          farmerNameEt.getText().toString(),priceET.getText().toString(),feildET.getText().toString(),
                qualityEt.getText().toString() ,startTimeEt.getText().toString(),endTimeET.getText().toString(),
                descriptionEt.getText().toString(),
                ImageUrl
        );

        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Product")
                .child(myCurrentDateTime).setValue(productData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(uploadProduct.this,"Product Uploaded", Toast.LENGTH_SHORT).show();
                         finish();
                     }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(uploadProduct.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}