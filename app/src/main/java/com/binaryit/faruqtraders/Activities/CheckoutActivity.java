package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.RetrofitClient;
import com.binaryit.faruqtraders.API.RetrofitClientForDelivery;
import com.binaryit.faruqtraders.Adapter.DeliveryMethodAdapter;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.DeliveryMethodResponse;
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
    TextView nextTextView;
    TextView subtotalAmount, grandTotalTextView;



    RadioGroup radioGroup;
    RadioButton radioButton;

    int subtotalAmountInt;
    int grandTotal;
    int deliveryChargeInsideDhaka = 80, deliveryChargeOutsideDhaka = 150, deliveryChargeInGulshanBanani = 0 ;

    RecyclerView deliveryTypeRecyclerView;
    DeliveryMethodAdapter adapter;
    List<DeliveryMethodResponse> deliveryMethodResponseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initialization();
        //fetchDeliveryMethod();

        if (getIntent().getStringExtra("grandTotal") != null) {
            subtotalAmountInt = Integer.parseInt(getIntent().getStringExtra("grandTotal"));
            subtotalAmount.setText(String.valueOf(subtotalAmountInt) +" ৳");
        }

        setListener();
        received_product_details();

        deliveryTypeRecyclerView = findViewById(R.id.deliveryTypeRecyclerView);
        deliveryTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DeliveryMethodAdapter(this, deliveryMethodResponseList);
        deliveryTypeRecyclerView.setAdapter(adapter);

        RetrofitClientForDelivery.getRetrofitClient().getDeliveryCharge().enqueue(new Callback<List<DeliveryMethodResponse>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<DeliveryMethodResponse>> call, Response<List<DeliveryMethodResponse>> response) {
                if (response.body() != null){
                    deliveryMethodResponseList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    System.out.println("List Size is " + deliveryMethodResponseList.size());
                }
                else {
                    System.out.println("Else : " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<DeliveryMethodResponse>> call, Throwable t) {
                System.out.println("Failure t : " + t.getMessage().toString());
            }
        });

    }



    private void initialization(){

        imageView = findViewById(R.id.imageBack);

        radioGroup = findViewById(R.id.radioGroup);
        nextTextView = findViewById(R.id.nextTextView);
        subtotalAmount = findViewById(R.id.subtotalAmout);
        grandTotalTextView = findViewById(R.id.grandTotalTextView);



    }

    private void setListener(){
        imageView.setOnClickListener(this);
        nextTextView.setOnClickListener(this);
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

            case R.id.nextTextView:
                sendUserDetails();
        }
    }

    private void sendUserDetails() {
        Intent intent = new Intent(getApplicationContext(), CheckoutNextActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        switch (radioId){
            case R.id.radio_one:

                grandTotal = subtotalAmountInt + deliveryChargeInsideDhaka;
                grandTotalTextView.setText(String.valueOf(grandTotal) +" ৳");
                System.out.println("Grand total " + grandTotal +" ৳");
                break;

            case R.id.radio_two:
                grandTotal = subtotalAmountInt + deliveryChargeOutsideDhaka;
                grandTotalTextView.setText(String.valueOf(grandTotal)+" ৳");
                break;

            case R.id.radio_three:
                grandTotal = subtotalAmountInt + deliveryChargeInGulshanBanani;
                grandTotalTextView.setText(String.valueOf(grandTotal)+" ৳");
                break;

        }
        /*Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(),
                Toast.LENGTH_SHORT).show();*/
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void fetchDeliveryMethod() {
        RetrofitClient.getRetrofitClient().getDeliveryCharge().enqueue(new Callback<List<DeliveryMethodResponse>>() {
            @Override
            public void onResponse(Call<List<DeliveryMethodResponse>> call, Response<List<DeliveryMethodResponse>> response) {
                if (response.body() != null){
                    //deliveryMethodResponse = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<DeliveryMethodResponse>> call, Throwable t) {

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