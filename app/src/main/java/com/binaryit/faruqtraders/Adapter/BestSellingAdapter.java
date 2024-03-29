package com.binaryit.faruqtraders.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.binaryit.faruqtraders.Activities.ProductDetailsActivity;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.ApiResponseModel;

public class BestSellingAdapter extends RecyclerView.Adapter<BestSellingAdapter.BestSellerViewHolder> {

    Context context;
    ApiResponseModel apiResponseModel;

    public BestSellingAdapter(Context context, ApiResponseModel apiResponseModel) {
        this.context = context;
        this.apiResponseModel = apiResponseModel;
    }

    public BestSellingAdapter() {
    }

    @NonNull
    @Override
    public BestSellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent , false);
        return new BestSellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellerViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (apiResponseModel.products.data.size() > 0){
            holder.name.setText(apiResponseModel.products.data.get(position).name);
            //holder.category.setText(data.products.data.get(position).slug);
            holder.main_price.setText(apiResponseModel.products.data.get(position).price + "৳");
            holder.discount_price.setText(apiResponseModel.products.data.get(position).discounted_price.toString() + "৳");
            holder.main_price.setPaintFlags(holder.main_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            Glide.with(context).load(apiResponseModel.products.data.get(position).thumbnail).into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailsActivity.class);
                    //intent.putExtra("position", holder.getAdapterPosition());
                    intent.putExtra("name", apiResponseModel.products.data.get(position).name);
                    intent.putExtra("main_price", apiResponseModel.products.data.get(position).price);
                    intent.putExtra("discount_price", apiResponseModel.products.data.get(position).discounted_price.toString());
                    intent.putExtra("thumbnail", apiResponseModel.products.data.get(position).thumbnail);
                    intent.putExtra("slug", apiResponseModel.products.data.get(position).slug);
                    intent.putExtra("id", apiResponseModel.products.data.get(position).id);
                    context.startActivity(intent);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return apiResponseModel.products.data.size();
    }

    public class BestSellerViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView name, category, main_price, discount_price;
        public BestSellerViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.latest_product_name);
            //category = itemView.findViewById(R.id.product_category);
            main_price = itemView.findViewById(R.id.product_main_price);
            discount_price = itemView.findViewById(R.id.product_discount_price);

        }
    }

}
