package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.TextView;

import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.EachProductResponse;
import com.binaryit.faruqtraders.Utility.NetworkChangeListener;

import java.util.List;

public class EachProductActivity extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    TextView name, category, sku, main_price, discount_price;

    List<EachProductResponse> eachProductResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_product);

        name = findViewById(R.id.product_details_name);
        category = findViewById(R.id.product_category);
        sku = findViewById(R.id.product_sku_number);
        main_price = findViewById(R.id.product_main_price);
        discount_price = findViewById(R.id.product_discount_price);

    }

    private void received_product_details(){

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