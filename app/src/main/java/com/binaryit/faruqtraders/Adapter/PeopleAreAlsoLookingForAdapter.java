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
import com.binaryit.faruqtraders.Response.VisitedProductResponse;

public class PeopleAreAlsoLookingForAdapter extends RecyclerView.Adapter<PeopleAreAlsoLookingForAdapter.SuggestProductViewHolder> {

    Context context;
    VisitedProductResponse data;

    public PeopleAreAlsoLookingForAdapter(Context context, VisitedProductResponse data) {
        this.context = context;
        this.data = data;
    }

    public PeopleAreAlsoLookingForAdapter() {
    }

    @NonNull
    @Override
    public SuggestProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout , parent , false);
        return new SuggestProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SuggestProductViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (data.products.size() > 0){

            holder.name.setText(data.products.get(position).name);
            //holder.category.setText(data.products.data.get(position).slug);
            holder.main_price.setText(data.products.get(position).price + "৳");
            holder.discount_price.setText(data.products.get(position).discounted_price.toString() + "৳");
            holder.main_price.setPaintFlags(holder.main_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            Glide.with(context).load(data.products.get(position).thumbnail).into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailsActivity.class);
                    intent.putExtra("name", data.products.get(position).name);
                    intent.putExtra("main_price", data.products.get(position).price);
                    intent.putExtra("discount_price", data.products.get(position).discounted_price.toString());
                    intent.putExtra("thumbnail", data.products.get(position).thumbnail);
                    intent.putExtra("id", data.products.get(position).id);
                    intent.putExtra("slug", data.products.get(position).slug);
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return data.products.size();
    }


    public class SuggestProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView name, category, discount_price, main_price;

        public SuggestProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.latest_product_name);
            //category = itemView.findViewById(R.id.product_category);
            discount_price = itemView.findViewById(R.id.product_discount_price);
            main_price = itemView.findViewById(R.id.product_main_price);

        }
    }

}
