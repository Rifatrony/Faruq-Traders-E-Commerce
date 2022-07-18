package com.example.faruqtraders.Activities;

import static com.example.faruqtraders.Activities.AllCategoryActivity.apiResponseData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.faruqtraders.API.ApiInterface;
import com.example.faruqtraders.API.RetrofitClient;
import com.example.faruqtraders.API.RetrofitClientWithHeader;
import com.example.faruqtraders.Adapter.RelatedProductAdapter;

import com.example.faruqtraders.R;
import com.example.faruqtraders.Response.AddCartResponse;
import com.example.faruqtraders.Response.AddToCartPostModel;
import com.example.faruqtraders.Response.AddToCartResponse;
import com.example.faruqtraders.Response.ApiResponseModel;
import com.example.faruqtraders.Response.ProductDetailsResponseModel;
import com.example.faruqtraders.Utility.NetworkChangeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    TextView product_name, product_category, product_discount_price, product_details_main_price, quantityNumberTextView;
    ImageView imageView, brand_image;

    RecyclerView recyclerView;
    RelatedProductAdapter relatedProductAdapter;

    FloatingActionButton add_button, minus_button;
    AppCompatButton add_to_cart, add_to_favourite;
    AppCompatImageView imageBack;

    List<AddToCartResponse.Data> data;

    ApiInterface apiInterface;

    List<ProductDetailsResponseModel> productDetails1 = new ArrayList<>();
    ProductDetailsResponseModel productDetails;

    ApiResponseModel apiResponseModel;

    AddCartResponse addCartResponse;
    AddToCartPostModel addToCartPostModel;


    int count = 1;
    int quantity;
    String product_id;

    //LatestProductModel latestProductModel = null;

    //BestSellingModel bestSellingModel = null;

    //ApiResponseModel data = null;

    String name, main_price, discount_price, thumbnail, id, slug, category;
    int position;

    TextView relatedProductMoreProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initialization();
        //relateProduct();
        setListener();

        //fetchRelatedProduct();

        received_product_details();

        //setData();
        //position = getIntent().getIntExtra("position", 0);
        /*System.out.println("Product Name is "+ apiResponseData.products.data.get(position).name);*/
    }

    private void initialization() {

        apiInterface = RetrofitClient.getRetrofitClient();

        product_name = findViewById(R.id.product_details_product_name);
        product_category = findViewById(R.id.product_details_product_category);
        product_details_main_price = findViewById(R.id.product_details_product_main_price);
        product_discount_price = findViewById(R.id.product_details_product_dicount_price);
        imageView = findViewById(R.id.imageView);
        brand_image = findViewById(R.id.product_details_product_brand_image);

        recyclerView = findViewById(R.id.product_details_related_product_recyclerView);

        quantityNumberTextView = findViewById(R.id.quantityNumberTextView);
        relatedProductMoreProduct = findViewById(R.id.relatedProductMoreProduct);

        imageBack = findViewById(R.id.imageBackId);
        add_button = findViewById(R.id.add_button);
        minus_button = findViewById(R.id.minus_button);
        add_to_cart = findViewById(R.id.add_to_cart_button);
        add_to_favourite = findViewById(R.id.add_to_favourite_button);

    }

    private void fetchRelatedProduct() {

        //category = getIntent().getStringExtra("slug");
        System.out.println("Slug 1 ----- > " + category);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        apiInterface.getRelatedProduct(category).enqueue(new Callback<ApiResponseModel>() {
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {

                if (response.body() != null){
                    apiResponseData = response.body();
                    relatedProductAdapter = new RelatedProductAdapter(ProductDetailsActivity.this, apiResponseData);
                    recyclerView.setAdapter(relatedProductAdapter);
                }
            }

            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {

            }
        });
    }

    private void setListener(){
        add_button.setOnClickListener(this);
        minus_button.setOnClickListener(this);
        add_to_cart.setOnClickListener(this);
        add_to_favourite.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        relatedProductMoreProduct.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void received_product_details(){

        name = getIntent().getStringExtra("name");
        main_price = getIntent().getStringExtra("main_price");
        discount_price = getIntent().getStringExtra("discount_price");
        thumbnail = getIntent().getStringExtra("thumbnail");
        id = getIntent().getStringExtra("id");
        slug = getIntent().getStringExtra("slug");

        RetrofitClient.getRetrofitClient().getProductDetails(slug).enqueue(new Callback<ProductDetailsResponseModel>() {
            @Override
            public void onResponse(Call<ProductDetailsResponseModel> call, Response<ProductDetailsResponseModel> response) {
                if (response.body() != null){

                    productDetails = response.body();

                    product_name.setText(productDetails.name);
                    product_category.setText(productDetails.category.name);
                    Glide.with(getApplicationContext()).load(productDetails.brand.image).into(brand_image);
                    Glide.with(getApplicationContext()).load(productDetails.getThumbnail()).into(imageView);

                    category = productDetails.category.name;
                    product_details_main_price.setText(productDetails.price + " ৳");
                    product_discount_price.setText(productDetails.final_price + " ৳");
                    product_details_main_price.setPaintFlags(product_details_main_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    quantityNumberTextView.setText(String.valueOf(count));

                    recyclerView.setLayoutManager(new LinearLayoutManager(ProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    apiInterface.getRelatedProduct(category).enqueue(new Callback<ApiResponseModel>() {
                        @Override
                        public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {

                            if (response.body() != null){
                                apiResponseData = response.body();
                                relatedProductAdapter = new RelatedProductAdapter(ProductDetailsActivity.this, apiResponseData);
                                recyclerView.setAdapter(relatedProductAdapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponseModel> call, Throwable t) {

                        }
                    });
                }
                else {
                    System.out.println("Error=========>" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ProductDetailsResponseModel> call, Throwable t) {
                System.out.println("Failure========>" + t.getMessage());
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.imageBackId:
                onBackPressed();
                break;

            case R.id.add_button:
                increaseCount();
                break;

            case R.id.minus_button:
                decreaseCount();
                break;

            case R.id.add_to_cart_button:
                addToCart();
                break;

            case R.id.add_to_favourite_button:
                break;

            case R.id.relatedProductMoreProduct:
                relatedMoreProduct();
                break;

            default:
        }
    }

    private void relatedMoreProduct(){
        startActivity(new Intent(getApplicationContext(), TopCategoryActivity.class));
    }

    /*Added to the cart*/

    public void addToCart(){

        String quantity = quantityNumberTextView.getText().toString().trim();

        AddToCartPostModel.Options options = new AddToCartPostModel.Options("Large", "Long");
        AddToCartPostModel model = new AddToCartPostModel(Integer.parseInt(quantity), options);

        System.out.println("ID IS "+id);

        RetrofitClientWithHeader.getRetrofitClient(this).addToCart(id, model).enqueue(new Callback<AddCartResponse>() {
            @Override
            public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {

                if (response.body() != null) {

                    if (response.code() == 200) {

                        Toast.makeText(ProductDetailsActivity.this, "Added To cart", Toast.LENGTH_SHORT).show();

                    }
                }



            }

            @Override
            public void onFailure(Call<AddCartResponse> call, Throwable t) {

                System.out.println("ERROR: "+t.getMessage());

            }
        });





    }

    private void increaseCount(){
        count++;
        quantityNumberTextView.setText(String.valueOf(count));
    }

    private void decreaseCount(){
        if (count == 1){

            return;
        }
        else {
            count = count-1;
            quantityNumberTextView.setText(String.valueOf(count));
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