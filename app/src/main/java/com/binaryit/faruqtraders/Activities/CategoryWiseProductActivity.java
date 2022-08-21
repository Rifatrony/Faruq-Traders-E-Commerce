package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

    FrameLayout frameLayout;

    String slug, name, icon;
    public int page = 1;

    RecyclerView recyclerView;
    TextView titleTextView;
    AppCompatImageView imageBack;
    ImageView nextPageButton, previousPageButton;
    ConstraintLayout constrainLayout;
    TextView pageNumberEditText;

    CategoryDetailsAdapter detailsAdapter;
    ApiInterface apiInterface;
    ApiResponseModel apiResponseModel;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_product_acivity);

        initialization();
        setListener();

        titleTextView.setText(name);
    }

    private void initialization(){

        apiInterface = RetrofitClient.getRetrofitClient();

        progressDialog = new ProgressDialog(this);

        imageBack = findViewById(R.id.imageBackId);
        pageNumberEditText = findViewById(R.id.pageNumberEditText);
        nextPageButton = findViewById(R.id.nextPageButton);
        previousPageButton = findViewById(R.id.previousPageButton);
        constrainLayout = findViewById(R.id.constrainLayout);
        frameLayout = findViewById(R.id.frameLayout);

        titleTextView = findViewById(R.id.title);
        recyclerView = findViewById(R.id.eachCategoryRecyclerView);

        fetchCategories();

    }

    private void setListener(){
        imageBack.setOnClickListener(this);
        nextPageButton.setOnClickListener(this);
        previousPageButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBackId:
                onBackPressed();
                break;

            case R.id.nextPageButton:
                page = page + 1;
                fetchCategories();
                break;

            case R.id.previousPageButton:
                page = page - 1;
                fetchCategories();
                break;
            default:
                return;
        }
    }

    private void fetchCategories() {

        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        slug = getIntent().getStringExtra("slug");
        name = getIntent().getStringExtra("name");
        icon = getIntent().getStringExtra("icon");

        recyclerView.setLayoutManager(new GridLayoutManager(CategoryWiseProductActivity.this, 2));
        apiInterface.getCategoryWiseProduct(slug, page).enqueue(new Callback<ApiResponseModel>() {

            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {

                if (response.body() != null){

                    progressDialog.dismiss();
                   try {
                       apiResponseModel = response.body();
                       System.out.println("current page is ----- >" + apiResponseModel.products.pagination.current_page);

                       pageNumberEditText.setText("Page " + apiResponseModel.products.pagination.current_page
                               + " of " + apiResponseModel.products.pagination.total_pages);
                       System.out.println("Total page is : " + apiResponseModel.products.pagination.total_pages);

                       if (apiResponseModel.products.pagination.current_page == page){
                           detailsAdapter = new CategoryDetailsAdapter(CategoryWiseProductActivity.this, apiResponseModel);
                           recyclerView.setAdapter(detailsAdapter);
                           detailsAdapter.notifyDataSetChanged();
                           previousPageButton.setEnabled(true);
                           previousPageButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                       }

                       if (apiResponseModel.products.pagination.current_page == 1){
                           System.out.println("This is the first page");
                           previousPageButton.setEnabled(false);
                       }

                       if (apiResponseModel.products.pagination.total_pages > 1){
                           System.out.println(String.valueOf(page + 1) + " is the next page");
                           detailsAdapter = new CategoryDetailsAdapter(CategoryWiseProductActivity.this, apiResponseModel);
                           recyclerView.setAdapter(detailsAdapter);
                           detailsAdapter.notifyDataSetChanged();
                           nextPageButton.setEnabled(true);
                       }

                       if (apiResponseModel.products.pagination.total_pages == apiResponseModel.products.pagination.current_page){
                           System.out.println("This is the last page");
                           nextPageButton.setEnabled(false);
                       }

                       /*if (apiResponseModel.products.pagination.current_page == apiResponseModel.products.pagination.total_pages){
                           constrainLayout.setVisibility(View.INVISIBLE);
                       }*/

                   }
                   catch (Exception e){

                   }
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