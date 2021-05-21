package com.example.cultivo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {
        TextView productDescription;
        ImageView productImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        productDescription = (TextView)findViewById(R.id.txtDescription);
        productImage = (ImageView)findViewById(R.id.ivImage);

        Bundle pBundle = getIntent().getExtras();

        if(pBundle != null){
            productDescription.setText(pBundle.getString("Description"));
            productImage.setImageResource(pBundle.getInt("Image"));
        }
    }
}