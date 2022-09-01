package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.RetrofitClient;
import com.binaryit.faruqtraders.API.RetrofitClientWithHeader;
import com.binaryit.faruqtraders.Adapter.DeliveryMethodAdapter;
import com.binaryit.faruqtraders.Adapter.OrderAdapter;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.OrderDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatImageView imageBack;
    RecyclerView orderRecyclerView;
    OrderDetailsResponse orderDetailsResponse;
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initialization();

        setListener();

        FetchOrder();
        orderRecyclerView.setHasFixedSize(true);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void FetchOrder() {
        RetrofitClientWithHeader.getRetrofitClient(this).getOrder().enqueue(new Callback<OrderDetailsResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                if (response.body() != null && response.isSuccessful()){
                    orderDetailsResponse = response.body();
                    orderAdapter = new OrderAdapter(OrderActivity.this, orderDetailsResponse);
                    orderRecyclerView.setAdapter(orderAdapter);
                }
                else {
                    Toast.makeText(OrderActivity.this, "No Order Found", Toast.LENGTH_SHORT).show();
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                Toast.makeText(OrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListener() {
        imageBack.setOnClickListener(this);
    }

    private void initialization() {
        imageBack = findViewById(R.id.imageBack);
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBack:
                onBackPressed();
                break;

        }
    }
}