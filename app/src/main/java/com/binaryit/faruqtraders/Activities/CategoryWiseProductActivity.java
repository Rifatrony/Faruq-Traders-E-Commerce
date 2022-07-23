package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.ApiInterface;
import com.binaryit.faruqtraders.API.RetrofitClient;
import com.binaryit.faruqtraders.Adapter.CategoryDetailsAdapter;

import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.ApiResponseModel;
import com.binaryit.faruqtraders.Response.CategoryResponseModel;
import com.binaryit.faruqtraders.Response.FilterResponseModel;
import com.binaryit.faruqtraders.Response.VisitedProductResponse;
import com.binaryit.faruqtraders.Utility.NetworkChangeListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryWiseProductActivity extends AppCompatActivity implements View.OnClickListener {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    CategoryResponseModel categoryModel;
    FilterResponseModel filterResponseModel;
    String slug, name, icon;
    int position;
    private int page = 1;
    private static int per_page = 30;

    RecyclerView recyclerView;
    TextView titleTextView;
    AppCompatImageView imageBack;

    CategoryDetailsAdapter detailsAdapter;
    ApiInterface apiInterface;
    VisitedProductResponse visitedProductResponse;
    ApiResponseModel apiResponseModel;

    ProgressDialog progressDialog;

    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_product_acivity);

        initialization();

        setListener();


        //position = getIntent().getIntExtra("position", 0);

        titleTextView.setText(name);
        setUpPagination(true);
    }

    private void initialization(){

        apiInterface = RetrofitClient.getRetrofitClient();

        progressDialog = new ProgressDialog(this);

        imageBack = findViewById(R.id.imageBackId);

        titleTextView = findViewById(R.id.title);
        recyclerView = findViewById(R.id.eachCategoryRecyclerView);

        nestedScrollView = findViewById(R.id.nestedScrollView);

        fetchCategories(page);

    }

    private void setUpPagination(boolean isPaginationAllowed) {

        if (isPaginationAllowed){
            nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                    page = page + 1;
                    fetchCategories(page);
                }
            });
        }

        else {
            nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            });
        }
    }

    private void setListener(){
        imageBack.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBackId:
                onBackPressed();
                break;
            default:
                return;
        }
    }

    private void fetchCategories(int page) {

        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        slug = getIntent().getStringExtra("slug");
        name = getIntent().getStringExtra("name");
        icon = getIntent().getStringExtra("icon");

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        apiInterface.getCategoryWiseProduct(slug, page).enqueue(new Callback<ApiResponseModel>() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {

                if (response.body() != null){

                    progressDialog.dismiss();
                    apiResponseModel = response.body();

                    System.out.println("current page is ----- >" + apiResponseModel.products.pagination.current_page);

                    detailsAdapter = new CategoryDetailsAdapter(CategoryWiseProductActivity.this, apiResponseModel);
                    recyclerView.setAdapter(detailsAdapter);
                    detailsAdapter.notifyDataSetChanged();

                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(CategoryWiseProductActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CategoryWiseProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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