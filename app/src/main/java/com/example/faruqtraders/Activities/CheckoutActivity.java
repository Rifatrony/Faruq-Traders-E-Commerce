package com.example.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faruqtraders.API.RetrofitClient;
import com.example.faruqtraders.R;
import com.example.faruqtraders.Response.DeliveryMethodResponse;
import com.example.faruqtraders.Utility.NetworkChangeListener;

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

    DeliveryMethodResponse deliveryMethodResponse;

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

        fetchDeliveryMethod();

        System.out.println("Check Delivery");



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

        System.out.println("Receive name is ====> " + name + "\n Receive email is =====> " + email);

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
        RetrofitClient.getRetrofitClient().getDeliveryCharge().enqueue(new Callback<DeliveryMethodResponse>() {
            @Override
            public void onResponse(Call<DeliveryMethodResponse> call, Response<DeliveryMethodResponse> response) {
                if (response.body() != null){
                    deliveryMethodResponse = response.body();
                    System.out.println("Response is ------>  "+ response.body());
                }
            }

            @Override
            public void onFailure(Call<DeliveryMethodResponse> call, Throwable t) {
                System.out.println("Failure : " + t.getMessage());
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