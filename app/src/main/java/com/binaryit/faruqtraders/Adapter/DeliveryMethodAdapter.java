package com.binaryit.faruqtraders.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.DeliveryMethodResponse;

import java.util.List;

public class DeliveryMethodAdapter extends RecyclerView.Adapter<DeliveryMethodAdapter.DeliveryMethodViewHolder> {

    Context context;
    List<DeliveryMethodResponse> deliveryMethodResponseList;

    public DeliveryMethodAdapter() {
    }

    public DeliveryMethodAdapter(Context context, List<DeliveryMethodResponse> deliveryMethodResponseList) {
        this.context = context;
        this.deliveryMethodResponseList = deliveryMethodResponseList;
    }

    @NonNull
    @Override
    public DeliveryMethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delivery_layout, parent, false);
        return new DeliveryMethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryMethodViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameTextView.setText(deliveryMethodResponseList.get(position).getName());
        holder.rateTextView.setText(deliveryMethodResponseList.get(position).getRate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(deliveryMethodResponseList.get(position).getId());
                Toast.makeText(context,deliveryMethodResponseList.get(position).getName() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryMethodResponseList.size();
    }

    public class DeliveryMethodViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, rateTextView;

        public DeliveryMethodViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
            rateTextView = itemView.findViewById(R.id.rateTextView);
        }
    }

}
