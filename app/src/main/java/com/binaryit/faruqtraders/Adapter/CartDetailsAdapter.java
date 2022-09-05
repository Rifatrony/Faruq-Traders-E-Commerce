package com.binaryit.faruqtraders.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.binaryit.faruqtraders.API.RetrofitClientWithHeader;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.RecyclerViewInterface.CartAdapterInterface;
import com.binaryit.faruqtraders.Response.CartResponseModel;
import com.binaryit.faruqtraders.Response.DeleteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartDetailsAdapter extends RecyclerView.Adapter<CartDetailsAdapter.CartViewHolder> {

    Context context;
    CartResponseModel cartResponseModel;
    int subTotal;
    CartAdapterInterface cartAdapterInterface;
    int quantity = 0;
    double eachPrice = 0;

    public CartDetailsAdapter(Context context, CartResponseModel cartResponseModel, CartAdapterInterface cartAdapterInterface) {
        this.context = context;
        this.cartResponseModel = cartResponseModel;
        this.cartAdapterInterface = cartAdapterInterface;
    }

    public CartDetailsAdapter() {
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (cartResponseModel.data.size() > 0 ){

            try {

                holder.name.setText(cartResponseModel.data.get(position).product.name);
                holder.price.setText(cartResponseModel.data.get(position).price);
                holder.quantity.setText(cartResponseModel.data.get(position).quantity);

                Glide.with(context).load(cartResponseModel.data.get(position).product.thumbnail).into(holder.cartProductImage);

                subTotal = (int)cartResponseModel.data.get(position).total;

                holder.sub_total_amount_each_cart.setText(String.valueOf(subTotal+".0"));

                eachPrice = Double.parseDouble(cartResponseModel.data.get(position).price);
                quantity = Integer.parseInt(cartResponseModel.data.get(position).quantity);
                System.out.println("Each Price is " + eachPrice);
                System.out.println("Quantity is " + quantity);

                String id = cartResponseModel.data.get(position).product.id;

                holder.deleteCartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        RetrofitClientWithHeader.getRetrofitClient(context).deleteAItem(cartResponseModel.data.get(position).product.id).enqueue(new Callback<DeleteResponse>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(context, "One Item Deleted from cart", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResponse> call, Throwable t) {

                            }
                        });
                    }
                });

                holder.increase_image_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        quantity = quantity + 1;
                        System.out.println("Update Quantity is : " + quantity);

                        holder.quantity.setText(String.valueOf(quantity));

                        RetrofitClientWithHeader.getRetrofitClient(context).incrementCart(id).enqueue(new Callback<CartResponseModel>() {
                            @Override
                            public void onResponse(Call<CartResponseModel> call, Response<CartResponseModel> response) {
                                if (response.body() != null && response.code() == 200){

                                }
                                else {
                                }

                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<CartResponseModel> call, Throwable t) {
                            }
                        });
                    }
                });

                holder.decrease_image_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        quantity = quantity - 1;
                        holder.quantity.setText(String.valueOf(quantity));

                        RetrofitClientWithHeader.getRetrofitClient(context).decrementCart(id).enqueue(new Callback<CartResponseModel>() {
                            @Override
                            public void onResponse(Call<CartResponseModel> call, Response<CartResponseModel> response) {
                                if (response.body() != null && response.code() == 200){

                                }
                            }

                            @Override
                            public void onFailure(Call<CartResponseModel> call, Throwable t) {

                            }
                        });
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return cartResponseModel.data.size();
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

}
