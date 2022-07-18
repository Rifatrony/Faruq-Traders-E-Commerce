package com.example.faruqtraders.Activities;

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

import com.example.faruqtraders.R;
import com.example.faruqtraders.Session.SessionManagement;
import com.example.faruqtraders.Utility.NetworkChangeListener;

public class CheckoutNextActivity extends AppCompatActivity implements View.OnClickListener {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    AppCompatImageView imageView;
    EditText nameEditText, emailEditText, numberEditText;
    AppCompatButton placeOrderButton;

    String name, email;
    int deliveryChargeInsideDhaka;
    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_next);

        initialization();
        setListener();
        received_product_details();

        deliveryChargeInsideDhaka = getIntent().getIntExtra("charge",1);
        System.out.println("charge is === >" + deliveryChargeInsideDhaka);




    }

    private void initialization(){
        imageView = findViewById(R.id.imageBack);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        numberEditText = findViewById(R.id.numberEditText);
        placeOrderButton = findViewById(R.id.placeOrderButton);


        sessionManagement = new SessionManagement(this);

    }

    private void setListener(){
        imageView.setOnClickListener(this);
        placeOrderButton.setOnClickListener(this);

    }

    private void received_product_details(){
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        nameEditText.setText(name);
        emailEditText.setText(email);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imageBack:
                onBackPressed();
                return;
            case R.id.placeOrderButton:
                goForOrder();
                break;
            default:
                break;

        }
    }

    private void goForOrder() {
        Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
        startActivity(intent);
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