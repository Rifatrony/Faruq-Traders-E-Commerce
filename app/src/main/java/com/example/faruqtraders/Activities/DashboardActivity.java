package com.example.faruqtraders.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faruqtraders.API.RetrofitClient;
import com.example.faruqtraders.MainActivity;
import com.example.faruqtraders.R;
import com.example.faruqtraders.Response.UserDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    CardView orderCard, accountsCard, wishlistCard;

    Toolbar toolbar;

    TextView userNameText, goToHomeTextView, emailTextView, phoneTextView, addressTextView;
    TextView accountsTextView, orderTextView, wishListTextView;

    UserDetailsResponse userDetailsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initialization();
        setListener();

        getUserDetails();

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /*Receive user details*/
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");

        System.out.println("............................\n Receive Name : " + name + "\n Receive Email : "+ email + "\n Receive Phone : " + phone);

    }

    private void getUserDetails() {

        RetrofitClient.getRetrofitClient().getUserDetails().enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                if (response.body() != null){

                    userDetailsResponse = response.body();
                    System.out.println("Name is=========>" + userDetailsResponse.getUser().getName());
                    System.out.println("Name is=========>" + userDetailsResponse.user.email);
                    Toast.makeText(DashboardActivity.this, "Success", Toast.LENGTH_SHORT).show();

                }

                else {
                    Toast.makeText(DashboardActivity.this, "Error "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Failure " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initialization() {
        toolbar = findViewById(R.id.toolBar);
        userNameText = findViewById(R.id.userNameText);
        emailTextView = findViewById(R.id.emailText);
        phoneTextView = findViewById(R.id.phoneText);
        goToHomeTextView = findViewById(R.id.goToHomeTextView);


        accountsTextView = findViewById(R.id.accountsTextView);
        orderTextView = findViewById(R.id.orderTextView);
        wishListTextView = findViewById(R.id.wishListTextView);
    }

    private void setListener() {

        accountsTextView.setOnClickListener(this);
        orderTextView.setOnClickListener(this);
        wishListTextView.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.dashboard_navigation_item, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_order:
                Toast.makeText(this, "Order", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_account_details:
                startActivity(new Intent(getApplicationContext(), AccountDetailsActivity.class));
                break;

            case R.id.nav_change_password:
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
                Toast.makeText(this, "nav_change_password", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_wishlist:
                startActivity(new Intent(getApplicationContext(), WishlistActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.goToHomeTextView:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;

            case R.id.orderTextView:
                startActivity(new Intent(getApplicationContext(), OrderActivity.class));
                break;

            case R.id.accountsTextView:
                startActivity(new Intent(getApplicationContext(), AccountDetailsActivity.class));
                break;

            case R.id.wishListTextView:
                startActivity(new Intent(getApplicationContext(), WishlistActivity.class));
                break;

        }
    }
}