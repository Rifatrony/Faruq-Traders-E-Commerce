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

public class TopCategoriesMoreProductAdapter extends RecyclerView.Adapter<TopCategoriesMoreProductAdapter.TopCategoriesMoreProductViewHolder> {

    Context context;
    ApiResponseModel data;

    public TopCategoriesMoreProductAdapter(Context context, ApiResponseModel data) {
        this.context = context;
        this.data = data;
    }

    public TopCategoriesMoreProductAdapter() {
    }

    @NonNull
    @Override
    public TopCategoriesMoreProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.top_in_category_more_product_layout, parent, false);

        return new TopCategoriesMoreProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TopCategoriesMoreProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (data.products.data.size() > 0){
            holder.name.setText(data.products.data.get(position).name);
            //holder.category.setText(data.products.data.get(position).slug);
            holder.main_price.setText(data.products.data.get(position).price + "৳");
            holder.discount_price.setText(data.products.data.get(position).discounted_price.toString() + "৳");
            holder.main_price.setPaintFlags(holder.main_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            Glide.with(context).load(data.products.data.get(position).thumbnail).into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailsActivity.class);
                    intent.putExtra("name", data.products.data.get(position).name);
                    intent.putExtra("main_price", data.products.data.get(position).price);
                    intent.putExtra("discount_price", data.products.data.get(position).discounted_price.toString());
                    intent.putExtra("thumbnail", data.products.data.get(position).thumbnail);
                    intent.putExtra("slug", data.products.data.get(position).slug);
                    intent.putExtra("id", data.products.data.get(position).id);
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        //return (data.products.data == null) ? 0 : data.products.data.size();
        return data.products.data.size();
    }

    public class TopCategoriesMoreProductViewHolder extends RecyclerView.ViewHolder{

        TextView name, main_price, discount_price;
        ImageView imageView;

        public TopCategoriesMoreProductViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.top_in_categories_more_product_name);
            main_price = itemView.findViewById(R.id.main_price);
            discount_price = itemView.findViewById(R.id.discount_price);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }

}
