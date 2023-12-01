package com.mca.ecommerceapplicationfinal.vansh.ui.home.Featured_Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mca.ecommerceapplicationfinal.vansh.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Set your UI components with the data from the Product object
        holder.textViewName.setText(product.getName());
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));

        String imageUrl = product.getImageURL();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Load the image using Glide
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.imageView);
        } else {
            // Set a default image when the URL is null or empty
            holder.imageView.setImageResource(R.drawable.shoe_sample);
        }
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPrice;
        ImageView imageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.productTitle);
            textViewPrice = itemView.findViewById(R.id.productPrice);
            imageView = itemView.findViewById(R.id.productImage);
        }
    }
}
