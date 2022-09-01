package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.RetrofitClient;
import com.binaryit.faruqtraders.API.RetrofitClientForDelivery;
import com.binaryit.faruqtraders.API.RetrofitClientWithHeader;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.DeliveryMethodResponse;
import com.binaryit.faruqtraders.Response.OrderResponse;
import com.binaryit.faruqtraders.Response.UserDetailsResponse;
import com.binaryit.faruqtraders.Session.SessionManagement;
import com.binaryit.faruqtraders.Utility.NetworkChangeListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutNextActivity extends AppCompatActivity implements View.OnClickListener {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    AppCompatImageView imageView;
    EditText nameEditText, emailEditText, numberEditText, addressEditText;
    AppCompatButton placeOrderButton;

    String name, email, number, address, delivery_method, id = "eyJpdiI6InBjOEVURjZOdFMrY1lOOUFTMmh0Nnc9PSIsInZhbHVlIjoiRWowYmNpYXFQY1FLdzhLL2dBLzFKUT09IiwibWFjIjoiODlmZDYzNjFmNTA4ZmFlZmIwYTBmNDI0NTQ5MzcxODIxMWNlYTk0ZjgzYmIzYWEyYTdlYTNkZDhhYTJmNmIzYSJ9";
    int deliveryChargeInsideDhaka;
    SessionManagement sessionManagement;

    public static UserDetailsResponse userDetailsResponse;
    List<DeliveryMethodResponse> deliveryMethodResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_next);

        initialization();
        setListener();

        deliveryChargeInsideDhaka = getIntent().getIntExtra("charge",1);
        System.out.println("charge is === >" + deliveryChargeInsideDhaka);

        RetrofitClientWithHeader.getRetrofitClient(this).getUserDetails().enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                if (response.isSuccessful()) {
                    userDetailsResponse = response.body();

                    assert userDetailsResponse != null;
                    nameEditText.setText(userDetailsResponse.user.name);
                    emailEditText.setText(userDetailsResponse.user.email);
                    numberEditText.setText(userDetailsResponse.user.phone);
                    addressEditText.setText(userDetailsResponse.user.address);
                }
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
            }
        });

    }

    private void initialization(){

        imageView = findViewById(R.id.imageBack);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        numberEditText = findViewById(R.id.numberEditText);
        addressEditText = findViewById(R.id.addressEditText);
        placeOrderButton = findViewById(R.id.placeOrderButton);

        sessionManagement = new SessionManagement(this);

    }

    private void setListener(){

        imageView.setOnClickListener(this);
        placeOrderButton.setOnClickListener(this);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imageBack:
                onBackPressed();
                break;

            case R.id.placeOrderButton:
                goForOrder();
                break;

            default:
                break;

        }
    }

    private void goForOrder() {

        name = nameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        number = numberEditText.getText().toString().trim();
        address = addressEditText.getText().toString().trim();
        delivery_method = "inside dhaka";

        System.out.println("\n\n\n" + name + "\n\n\n" + email+ "\n\n\n" + number+ "\n\n\n" + address+ "\n\n\n" + delivery_method);

        RetrofitClientWithHeader.getRetrofitClient(this).placeOrder(name, email, number, id, address).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.body()!= null && response.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                    startActivity(intent);
                    Toast.makeText(CheckoutNextActivity.this, "Order Successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    System.out.println(response.errorBody());
                    Toast.makeText(CheckoutNextActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(CheckoutNextActivity.this, "Failure " + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("\n\n\n " + t.getMessage());
            }
        });

    }

    @Override
    protected void onStart() {

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}