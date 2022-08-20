package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.RetrofitClient;
import com.binaryit.faruqtraders.Adapter.SearchProductAdapter;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.ApiResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    ImageView imageBack;
    TextView titleTextView;

    RecyclerView searchProductRecyclerView;
    SearchProductAdapter adapter;
    ApiResponseModel apiResponseModel;

    String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchText = getIntent().getStringExtra("search_text");

        imageBack = findViewById(R.id.imageBack);
        titleTextView = findViewById(R.id.titleTextView);

        if (!searchText.isEmpty()){
            titleTextView.setText(searchText);
        }

        searchProductRecyclerView = findViewById(R.id.searchProductRecyclerView);
        searchProductRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        searchProductRecyclerView.setHasFixedSize(true);

        RetrofitClient.getRetrofitClient().searchProduct(searchText).enqueue(new Callback<ApiResponseModel>() {
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                if (response.isSuccessful()){

                    apiResponseModel = response.body();
                    adapter = new SearchProductAdapter(SearchResultActivity.this, apiResponseModel);
                    searchProductRecyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {

            }
        });
    }
}