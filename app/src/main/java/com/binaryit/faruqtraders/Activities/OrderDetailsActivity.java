package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.RetrofitClientWithHeader;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.DetailsOrderResponse;
import com.binaryit.faruqtraders.Response.OrderDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {

    String order_id;
    DetailsOrderResponse detailsOrderResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


        order_id = getIntent().getStringExtra("order_id");

        RetrofitClientWithHeader.getRetrofitClient(this).getOrderDetails(order_id).enqueue(new Callback<DetailsOrderResponse>() {
            @Override
            public void onResponse(Call<DetailsOrderResponse> call, Response<DetailsOrderResponse> response) {
                if (response.isSuccessful()){
                    detailsOrderResponse = response.body();
                    System.out.println("Name is : " + detailsOrderResponse.name);
                    Toast.makeText(OrderDetailsActivity.this, "Have order Details", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(OrderDetailsActivity.this, "Error: "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailsOrderResponse> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this,"Failure: "+ t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}