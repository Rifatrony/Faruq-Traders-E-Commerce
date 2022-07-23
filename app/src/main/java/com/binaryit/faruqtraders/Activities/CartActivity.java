package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.ApiInterface;
import com.binaryit.faruqtraders.API.RetrofitClientWithHeader;
import com.binaryit.faruqtraders.Adapter.CartDetailsAdapter;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.RecyclerViewInterface.CartAdapterInterface;
import com.binaryit.faruqtraders.Response.CartResponseModel;
import com.binaryit.faruqtraders.Response.DeleteResponse;
import com.binaryit.faruqtraders.Session.SessionManagement;
import com.binaryit.faruqtraders.Utility.NetworkChangeListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements View.OnClickListener, CartAdapterInterface {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    AppCompatImageView imageView;
    TextView clearCartTextView;

    RecyclerView cartRecyclerView;
    TextView grand_total;
    CartResponseModel responseModel;
    AppCompatButton checkoutButton;

    int grand_total_amount = 0;
    CartResponseModel cartResponseModelList;
    ApiInterface apiInterface;
    CartDetailsAdapter adapter;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount acct;


    SessionManagement sessionManagement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initialization();
        setListener();
        fetchCartProduct();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(broadcastReceiver, new IntentFilter("MyTotalAmount"));


        System.out.println("Grand Total is  " + grand_total_amount);
        grand_total.setText(String.valueOf(grand_total_amount));

    }

    private void initialization() {

        apiInterface = RetrofitClientWithHeader.getRetrofitClient(this);

        clearCartTextView = findViewById(R.id.clearCartTextView);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setHasFixedSize(true);

        imageView = findViewById(R.id.imageBack);
        grand_total = findViewById(R.id.grand_total);

        checkoutButton = findViewById(R.id.checkoutButton);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        acct = GoogleSignIn.getLastSignedInAccount(this);

        sessionManagement = new SessionManagement(this);

    }

    private void setListener(){
        imageView.setOnClickListener(this);
        clearCartTextView.setOnClickListener(this);
        checkoutButton.setOnClickListener(this);
    }

    private void checkLogin(){

        if (!sessionManagement.getSessionModel().getUserPhone().equals("null")) {

            showToast("Go for Checkout");
            Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
            intent.putExtra("grandTotal",String.valueOf(grand_total_amount));
            startActivity(intent);

        } else {

            showToast("Need to Login");
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imageBack:
                onBackPressed();
                break;

            case R.id.checkoutButton:
                checkLogin();
                break;

            case R.id.clearCartTextView:
                ClearCart();
                break;

            default:

        }
    }

    private void ClearCart() {

        RetrofitClientWithHeader.getRetrofitClient(this).ClearCart().enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()){
                    showToast("Cart Clear");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {

            }
        });
    }

    private void fetchCartProduct() {

        apiInterface.getCartDetails().enqueue(new Callback<CartResponseModel>() {
            @Override
            public void onResponse(Call<CartResponseModel> call, Response<CartResponseModel> response) {

                if (response.body() != null) {

                    responseModel = response.body();
                    System.out.println("List size: "+responseModel.data.size());
                    setAdapter(responseModel);

                }
            }

            @Override
            public void onFailure(Call<CartResponseModel> call, Throwable t) {

            }
        });

    }

    private void setAdapter(CartResponseModel responseModel) {

        adapter = new CartDetailsAdapter(CartActivity.this, responseModel, this);
        cartRecyclerView.setAdapter(adapter);
        printGrandTotal(0);

    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            grand_total_amount = intent.getIntExtra("totalAmount", 0);
            grand_total.setText(grand_total_amount +" Tk.");
            System.out.println("Sub Total Amount is ======> " + grand_total_amount);
        }
    };

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    @SuppressLint("SetTextI18n")
    @Override
    public void setGrandTotal(int grandTotal) {
       // grand_total.setText(""+grandTotal);

        for (int i = 0; i < responseModel.data.size(); i++) {

            grand_total_amount = grand_total_amount + (int) responseModel.data.get(i).total;

        }

        System.out.println("GRAND: "+responseModel.data.get(0).total);


        grand_total.setText(String.valueOf(grandTotal));

    }

    public void printGrandTotal (int grandTotalAmount){

        for (int i = 0; i < responseModel.data.size(); i++) {
            grand_total_amount = grand_total_amount + (int) responseModel.data.get(i).total;
        }
        grand_total.setText(String.valueOf(grand_total_amount+" ৳"));

    }



}