package com.example.faruqtraders.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faruqtraders.Activities.CartActivity;
import com.example.faruqtraders.Model.CartModel;
import com.example.faruqtraders.R;
import com.example.faruqtraders.Response.CartResponseModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    List<CartModel> cartModelList;

    int totalAmount = 0, dec, total_dec;
    int sum = 0, count, each_price = 0;
    int number;

    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    public CartAdapter(CartActivity context, CartResponseModel cartResponseModelList) {
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CartModel data = cartModelList.get(position);
        holder.name.setText(data.getProduct_name());
        holder.price.setText(String.valueOf(data.getProduct_price()+" Tk."));
        holder.quantity.setText(String.valueOf(data.getProduct_quantity()));

        int finalResult = cartModelList.get(position).getProduct_price() * cartModelList.get(position).getProduct_quantity();
        holder.sub_total_amount_each_cart.setText(String.valueOf(finalResult) + " Tk.");

        holder.deleteCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Cart Deleted Clicked", Toast.LENGTH_SHORT).show();
                /*cartModelList.remove(position);
                notifyDataSetChanged();*/
            }
        });

        holder.increase_image_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                count = cartModelList.get(position).getProduct_quantity() ;
                //count = cartModelList.get(position).getProduct_quantity() ;
                increaseCount();
                holder.quantity.setText(String.valueOf(count));
                Toast.makeText(context, "Increase", Toast.LENGTH_SHORT).show();

            }
        });
        holder.decrease_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int decrease = Integer.parseInt(String.valueOf(holder.quantity.getText()));
                count = cartModelList.get(position).getProduct_quantity() ;
                decreaseCount();
                System.out.println("Decrease is " + dec);
                holder.quantity.setText(String.valueOf(count));
                Toast.makeText(context, "Decrease", Toast.LENGTH_SHORT).show();
            }
        });

        totalAmount = cartModelList.get(position).getProduct_price() * cartModelList.get(position).getProduct_quantity();
        sum = totalAmount + sum;

        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", sum);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, quantity, sub_total_amount_each_cart;
        ImageView cartProductImage, increase_image_button, decrease_image_button;

        ImageView deleteCartButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cartProductName);
            price = itemView.findViewById(R.id.cartProductPrice);
            quantity = itemView.findViewById(R.id.cartProductQuantity);
            cartProductImage = itemView.findViewById(R.id.cartProductImage);
            deleteCartButton = itemView.findViewById(R.id.deleteCartButtonId);
            increase_image_button = itemView.findViewById(R.id.increase_image_button);
            decrease_image_button = itemView.findViewById(R.id.decrease_image_button);
            sub_total_amount_each_cart = itemView.findViewById(R.id.sub_total_amount_each_cart);

        }
    }

    private void increaseCount(){
        count = count + 1;

    }
    private void decreaseCount(){
        count = count - 1;

    }
}
