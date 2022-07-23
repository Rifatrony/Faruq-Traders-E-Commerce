package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.binaryit.faruqtraders.API.ApiInterface;
import com.binaryit.faruqtraders.API.RetrofitClient;
import com.binaryit.faruqtraders.Adapter.TopCategoriesMoreProductAdapter;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.ApiResponseModel;
import com.binaryit.faruqtraders.Utility.NetworkChangeListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    AppCompatImageView imageView;
    RecyclerView top_in_category_recycler_view;
    TopCategoriesMoreProductAdapter adapter;

    ApiInterface apiInterface;
    ApiResponseModel apiResponseModel;

    ProgressDialog progressDialog;

    int page = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_category);

        initialization();
        setListener();

        fetchTopCategoryMoreProduceProduct();

    }

    private void initialization(){

        apiInterface = RetrofitClient.getRetrofitClient();

        progressDialog = new ProgressDialog(this);

        imageView = findViewById(R.id.imageBack);
        top_in_category_recycler_view = findViewById(R.id.top_in_categories_more_product_recyclerView);

    }

    private void setListener(){
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBack:
                onBackPressed();
                break;

            default:
                return;
        }
    }


    private void fetchTopCategoryMoreProduceProduct() {
        top_in_category_recycler_view.setLayoutManager(new GridLayoutManager(this, 3));

        progressDialog.show();
        progressDialog.setMessage("Loading Please Wait");
        progressDialog.setCancelable(false);

        apiInterface.getTopInCategories(1).enqueue(new Callback<ApiResponseModel>() {
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {

                progressDialog.dismiss();

                if (response.body() != null){
                    apiResponseModel = response.body();
                    adapter = new TopCategoriesMoreProductAdapter(TopCategoryActivity.this, apiResponseModel);
                    top_in_category_recycler_view.setAdapter(adapter);

                }
            }



            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {

               progressDialog.dismiss();

            }
        });
    }


    /*Check Internet Connectivity*/

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