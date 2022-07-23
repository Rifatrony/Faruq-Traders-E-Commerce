package com.binaryit.faruqtraders.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.EachProductResponse;

import java.util.List;

public class EachProductAdapter extends RecyclerView.Adapter<EachProductAdapter.EachProductViewHolder> {

    Context context;
    List<EachProductResponse> eachProductResponse;

    public EachProductAdapter(Context context, List<EachProductResponse> eachProductResponse) {
        this.context = context;
        this.eachProductResponse = eachProductResponse;
    }

    @NonNull
    @Override
    public EachProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_details_layout, parent, false);
        return new EachProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EachProductViewHolder holder, int position) {

        holder.name.setText(eachProductResponse.get(position).name);
       // holder.category.setText(eachProductResponse.get(position).category.image);
        holder.sku.setText(eachProductResponse.get(position).sku);
        holder.category.setText(eachProductResponse.get(position).category.name);
        holder.main_price.setText(eachProductResponse.get(position).price);
        holder.discount_price.setText(eachProductResponse.get(position).final_price+ " Tk.");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        return eachProductResponse.size();
    }

    public static class EachProductViewHolder extends RecyclerView.ViewHolder {

        TextView name, category, sku, main_price, discount_price;

        public EachProductViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_details_name);
            category = itemView.findViewById(R.id.product_category);
            sku = itemView.findViewById(R.id.product_sku_number);
            main_price = itemView.findViewById(R.id.product_main_price);
            discount_price = itemView.findViewById(R.id.product_discount_price);
        }
    }

}
