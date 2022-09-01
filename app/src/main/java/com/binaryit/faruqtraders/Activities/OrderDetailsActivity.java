package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.RetrofitClientWithHeader;
import com.binaryit.faruqtraders.Adapter.OrderAdapter;
import com.binaryit.faruqtraders.Adapter.OrderItemAdapter;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.DetailsOrderResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {

    String order_id;
    TextView orderIdTextView, statusTextView, addressTextView, subTotalTextView, deliveryChargeTextView,
            totalTextView, totalItemTextView, deliveryCategoryTextView;

    AppCompatImageView imageBack;
    RecyclerView itemRecyclerView;
    OrderItemAdapter orderItemAdapter;

    DetailsOrderResponse detailsOrderResponse;
    int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        itemRecyclerView = findViewById(R.id.itemRecyclerView);
        orderIdTextView = findViewById(R.id.orderIdTextView);
        statusTextView = findViewById(R.id.statusTextView);
        addressTextView = findViewById(R.id.addressTextView);
        subTotalTextView = findViewById(R.id.subTotalTextView);
        deliveryChargeTextView = findViewById(R.id.deliveryChargeTextView);
        totalTextView = findViewById(R.id.totalTextView);
        totalItemTextView = findViewById(R.id.totalItemTextView);
        deliveryCategoryTextView = findViewById(R.id.deliveryCategoryTextView);
        imageBack = findViewById(R.id.imageBack);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        order_id = getIntent().getStringExtra("order_id");
        System.out.println("Order id is ........" + order_id);

        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemRecyclerView.setHasFixedSize(true);

        RetrofitClientWithHeader.getRetrofitClient(this).getOrderDetails(order_id).enqueue(new Callback<DetailsOrderResponse>() {
            @Override
            public void onResponse(Call<DetailsOrderResponse> call, Response<DetailsOrderResponse> response) {
                if (response.isSuccessful()){

                    detailsOrderResponse = response.body();
                    assert detailsOrderResponse != null;
                    orderIdTextView.setText(detailsOrderResponse.orderid);
                    statusTextView.setText(detailsOrderResponse.status);
                    deliveryCategoryTextView.setText(detailsOrderResponse.delivery);
                    subTotalTextView.setText(detailsOrderResponse.sub_total);
                    deliveryChargeTextView.setText(detailsOrderResponse.delivery_rate);
                    addressTextView.setText(detailsOrderResponse.address);
                    totalTextView.setText(detailsOrderResponse.total);
                    size = detailsOrderResponse.items.size();
                    totalItemTextView.setText(String.valueOf(size));
                    System.out.println(String.valueOf(detailsOrderResponse.items.size()));
                    /*for (int i = 0; i < size; i++){
                        //itemNameTextView.setText(detailsOrderResponse.items.add());
                    }*/
                    orderItemAdapter = new OrderItemAdapter(OrderDetailsActivity.this, detailsOrderResponse);
                    itemRecyclerView.setAdapter(orderItemAdapter);

                }
                else {
                    System.out.println("Error: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<DetailsOrderResponse> call, Throwable t) {
                System.out.println("Failure "+t.getMessage());
                Toast.makeText(OrderDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}