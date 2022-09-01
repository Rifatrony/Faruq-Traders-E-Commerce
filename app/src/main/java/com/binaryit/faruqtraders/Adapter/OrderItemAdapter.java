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
import com.binaryit.faruqtraders.Response.DetailsOrderResponse;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ItemViewHolder> {

    Context context;
    DetailsOrderResponse detailsOrderResponse;

    public OrderItemAdapter(Context context, DetailsOrderResponse detailsOrderResponse) {
        this.context = context;
        this.detailsOrderResponse = detailsOrderResponse;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.nameTextView.setText(detailsOrderResponse.items.get(position).name);
        holder.quantityTextView.setText(detailsOrderResponse.items.get(position).quantity);
        holder.priceTextView.setText(detailsOrderResponse.items.get(position).price + " ৳");
        holder.totalTextView.setText(String.valueOf(detailsOrderResponse.items.get(position).total + " ৳"));
    }

    @Override
    public int getItemCount() {
        return detailsOrderResponse.items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, quantityTextView, priceTextView, totalTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
        }
    }
}
