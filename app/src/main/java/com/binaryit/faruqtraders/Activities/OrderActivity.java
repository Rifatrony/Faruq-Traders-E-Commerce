package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.RetrofitClient;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.OrderDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatImageView imageBack;
    RecyclerView orderRecyclerView;
    OrderDetailsResponse data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initialization();

        setListener();

        FetchOrder();
    }

    private void FetchOrder() {
        RetrofitClient.getRetrofitClient().getOrder().enqueue(new Callback<OrderDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                if (response.body() != null && response.isSuccessful()){
                    Toast.makeText(OrderActivity.this, data.data.size(), Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBack:
                onBackPressed();
                break;

        }
    }
}