package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.ApiInterface;
import com.binaryit.faruqtraders.API.RetrofitClient;
import com.binaryit.faruqtraders.Adapter.CategoriesAdapter;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.ApiResponseModel;
import com.binaryit.faruqtraders.Response.CategoryResponseModel;
import com.binaryit.faruqtraders.Utility.NetworkChangeListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    RecyclerView recyclerView;
    AppCompatImageView imageView;
    CategoriesAdapter categoriesAdapter;
    ApiInterface apiInterface;
    public static CategoryResponseModel data;
    public static ApiResponseModel apiResponseData;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        initialization();
        setDataToRecyclerView();
        setListener();

    }



    private void initialization() {

        apiInterface = RetrofitClient.getRetrofitClient();

        progressDialog = new ProgressDialog(this);

        recyclerView = findViewById(R.id.allCategoryRecyclerView);
        imageView = findViewById(R.id.imageBack);
    }

    private void setListener(){
        imageView.setOnClickListener(this);
    }



    private void setDataToRecyclerView() {

        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        apiInterface.getCategories().enqueue(new Callback<CategoryResponseModel>() {
            @Override
            public void onResponse(Call<CategoryResponseModel> call, Response<CategoryResponseModel> response) {
                progressDialog.dismiss();
                if (response.body() != null){
                    data = response.body();
                    categoriesAdapter = new CategoriesAdapter(AllCategoryActivity.this, data);
                    recyclerView.setAdapter(categoriesAdapter);

                }
            }

            @Override
            public void onFailure(Call<CategoryResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBack:
                onBackPressed();
                return;
        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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