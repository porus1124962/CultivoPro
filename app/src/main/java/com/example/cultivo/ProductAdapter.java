package com.example.cultivo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private Context pContext;
    private List<ProductData> myProductData;

    public ProductAdapter(Context pContext, List<ProductData> myProductData) {
        this.pContext = pContext;
        this.myProductData = myProductData;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View pView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row_item,viewGroup,false);
        return new ProductViewHolder(pView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
       //productViewHolder.imageView.setImageResource(myProductData.get(i).getItemImage());
        productViewHolder.pTitle.setText(myProductData.get(i).getItemName());
        productViewHolder.pDescription.setText(myProductData.get(i).getItemDescription());
        productViewHolder.pPrice.setText(myProductData.get(i).getItemPrice());

//

    }

    @Override
    public int getItemCount() { return myProductData.size(); }
}
class ProductViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView pTitle,pPrice,pDescription;
    CardView pCardView;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.ivImage);
        pTitle = itemView.findViewById(R.id.cardViewTitle);
        pDescription = itemView.findViewById(R.id.cardViewDescription);
        pPrice  =  itemView.findViewById(R.id.cardViewPrice);


    }
}