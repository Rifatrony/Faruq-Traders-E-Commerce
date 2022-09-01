package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.RetrofitClientWithHeader;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.DetailsOrderResponse;
import com.binaryit.faruqtraders.Response.OrderDetailsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {

    String order_id;

    List<DetailsOrderResponse> detailsOrderResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


        order_id = getIntent().getStringExtra("order_id");
        System.out.println("Order id is ........" + order_id);

        RetrofitClientWithHeader.getRetrofitClient(this).getOrderDetails(order_id).enqueue(new Callback<List<DetailsOrderResponse>>() {
            @Override
            public void onResponse(Call<List<DetailsOrderResponse>> call, Response<List<DetailsOrderResponse>> response) {
                if (response.body() != null){
                    detailsOrderResponseList = response.body();
                    System.out.println("Size is : " + detailsOrderResponseList.size());
                }
                if (response.body() == null){
                    System.out.println("Response is null");
                }

                else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<DetailsOrderResponse>> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this, "Failure " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}