package com.example.faruqtraders.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faruqtraders.R;
import com.example.faruqtraders.Response.OrderDetailsResponse;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.orderViewHolder> {

    Context context;
    List<OrderDetailsResponse> orderDetailsResponseList;

    public OrderAdapter(Context context, List<OrderDetailsResponse> orderDetailsResponseList) {
        this.context = context;
        this.orderDetailsResponseList = orderDetailsResponseList;
    }

    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.order_layout, parent, false);
        return new orderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
        /*Have to create model class then set the value*/
    }

    @Override
    public int getItemCount() {
        return orderDetailsResponseList.size();
    }

    public class orderViewHolder extends RecyclerView.ViewHolder {

        TextView order_tv, date_tv, status_tv, total_tv, action_tv;

        public orderViewHolder(@NonNull View itemView) {
            super(itemView);

            order_tv = itemView.findViewById(R.id.order_tv);
            date_tv = itemView.findViewById(R.id.date_tv);
            status_tv = itemView.findViewById(R.id.status_tv);
            total_tv = itemView.findViewById(R.id.total_tv);
            action_tv = itemView.findViewById(R.id.action_tv);

        }
    }

}
