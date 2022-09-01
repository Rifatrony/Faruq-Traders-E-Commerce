package com.binaryit.faruqtraders.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.binaryit.faruqtraders.Activities.OrderDetailsActivity;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.OrderDetailsResponse;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.orderViewHolder> {

    Context context;
    OrderDetailsResponse orderDetailsResponse;
    double amount;

    public OrderAdapter(Context context, OrderDetailsResponse orderDetailsResponse) {
        this.context = context;
        this.orderDetailsResponse = orderDetailsResponse;
    }

    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.order_layout, parent, false);
        return new orderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.order_tv.setText(orderDetailsResponse.data.get(position).orderid);
        holder.status_tv.setText(orderDetailsResponse.data.get(position).status);
        holder.quantity_tv.setText(String.valueOf(orderDetailsResponse.data.get(position).quantity));
        amount = orderDetailsResponse.data.get(position).amount;
        holder.amount_tv.setText(String.valueOf(amount));

        holder.order_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("order_id", orderDetailsResponse.data.get(position).id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDetailsResponse.data.size();
    }

    public class orderViewHolder extends RecyclerView.ViewHolder {

        TextView order_tv, status_tv, quantity_tv, amount_tv;

        public orderViewHolder(@NonNull View itemView) {
            super(itemView);

            order_tv = itemView.findViewById(R.id.order_tv);
            status_tv = itemView.findViewById(R.id.status_tv);
            quantity_tv = itemView.findViewById(R.id.quantity_tv);
            amount_tv = itemView.findViewById(R.id.amount_tv);

        }
    }

}
