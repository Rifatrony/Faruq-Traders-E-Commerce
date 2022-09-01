package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.binaryit.faruqtraders.API.ApiInterface;
import com.binaryit.faruqtraders.API.RetrofitClientForDelivery;
import com.binaryit.faruqtraders.API.RetrofitClientWithHeader;
import com.binaryit.faruqtraders.Adapter.DeliveryMethodAdapter;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.DeliveryMethodResponse;
import com.binaryit.faruqtraders.Session.SessionManagement;
import com.binaryit.faruqtraders.Utility.NetworkChangeListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    AppCompatImageView imageView;

    String name, email;
    TextView subtotalAmount;


    int subtotalAmountInt;

    RecyclerView deliveryTypeRecyclerView;
    DeliveryMethodAdapter adapter;

    List<DeliveryMethodResponse> deliveryMethodResponseList;

    ApiInterface apiInterface;
    SessionManagement sessionManagement;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initialization();

        if (getIntent().getStringExtra("grandTotal") != null) {
            subtotalAmountInt = Integer.parseInt(getIntent().getStringExtra("grandTotal"));
            subtotalAmount.setText(String.valueOf(subtotalAmountInt) +" ৳");
        }

        setListener();
        received_product_details();

        deliveryTypeRecyclerView = findViewById(R.id.deliveryTypeRecyclerView);
        deliveryTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        deliveryTypeRecyclerView.setHasFixedSize(true);
        deliveryMethodResponseList = new ArrayList<>();

        adapter = new DeliveryMethodAdapter(CheckoutActivity.this, deliveryMethodResponseList);
        deliveryTypeRecyclerView.setAdapter(adapter);

        sessionManagement = new SessionManagement(this);
        if (!sessionManagement.getSessionModel().getAccessToken().equals("null")){
            RetrofitClientWithHeader.getRetrofitClient(this).getDeliveryCharge().enqueue(new Callback<List<DeliveryMethodResponse>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<List<DeliveryMethodResponse>> call, Response<List<DeliveryMethodResponse>> response) {
                    if (response.body() != null && response.isSuccessful()){
                        deliveryMethodResponseList.addAll(response.body());
                        adapter = new DeliveryMethodAdapter(CheckoutActivity.this, deliveryMethodResponseList);
                        deliveryTypeRecyclerView.setAdapter(adapter);
                        System.out.println("List size "+ deliveryMethodResponseList.size());

                    }
                    else {
                        System.out.println("Else : " + response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<List<DeliveryMethodResponse>> call, Throwable t) {
                    System.out.println("Failure : " + t.getMessage());
                }
            });
        }
    }



    private void initialization(){

        apiInterface = RetrofitClientForDelivery.getRetrofitClient();

        imageView = findViewById(R.id.imageBack);

        subtotalAmount = findViewById(R.id.subtotalAmout);

    }

    private void setListener(){
        imageView.setOnClickListener(this);
    }

    private void received_product_details() {

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBack:
                onBackPressed();
                return;
        }
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