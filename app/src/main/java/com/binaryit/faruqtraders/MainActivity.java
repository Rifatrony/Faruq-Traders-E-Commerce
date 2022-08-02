package com.binaryit.faruqtraders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.binaryit.faruqtraders.API.ApiInterface;
import com.binaryit.faruqtraders.API.RetrofitClient;
import com.binaryit.faruqtraders.API.RetrofitClientWithHeader;
import com.binaryit.faruqtraders.Activities.AllCategoryActivity;
import com.binaryit.faruqtraders.Activities.CartActivity;
import com.binaryit.faruqtraders.Activities.ContactUsActivity;
import com.binaryit.faruqtraders.Activities.DashboardActivity;
import com.binaryit.faruqtraders.Activities.LoginActivity;
import com.binaryit.faruqtraders.Activities.OrderActivity;
import com.binaryit.faruqtraders.Activities.TopCategoryActivity;
import com.binaryit.faruqtraders.Activities.WishlistActivity;
import com.binaryit.faruqtraders.Adapter.BannerAdapter;
import com.binaryit.faruqtraders.Adapter.BestSellingAdapter;
import com.binaryit.faruqtraders.Adapter.FeatureAdapter;
import com.binaryit.faruqtraders.Adapter.ImageAdapter;
import com.binaryit.faruqtraders.Adapter.LatestProductAdapter;

import com.binaryit.faruqtraders.Adapter.PeopleAreAlsoLookingForAdapter;
import com.binaryit.faruqtraders.Adapter.SellProductAdapter;
import com.binaryit.faruqtraders.Adapter.TopInCategoriesAdapter;
import com.binaryit.faruqtraders.Model.ImageModel;
import com.binaryit.faruqtraders.Response.ApiResponseModel;
import com.binaryit.faruqtraders.Response.BannerResponse;
import com.binaryit.faruqtraders.Response.UserDetailsResponse;
import com.binaryit.faruqtraders.Response.VisitedProductResponse;
import com.binaryit.faruqtraders.Session.SessionManagement;
import com.binaryit.faruqtraders.Utility.NetworkChangeListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    private RecyclerView bannerRecyclerView,featureRecyclerView , bestSellingRecyclerView,
            sellProductRecyclerView, topInCategoriesRecyclerView, latestProductRecyclerView,
            peoplesAreAlsoLookingForRecyclerView;

    private long backPressedTime;
    private Toast backToast;

    LinearLayoutManager layoutManager;

    EditText searchEditText;
    TextView all_category_text_view, top_in_categories_more_product, more_product;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    //BottomNavigationView bottomNavigationView;

    ViewPager2 viewPager2;
    List<ImageModel> imageModelList;
    ImageAdapter adapter;
    Handler slideHandler = new Handler();

    LatestProductAdapter latestProductAdapter;
    FeatureAdapter featureAdapter;
    SellProductAdapter sellProductAdapter;
    TopInCategoriesAdapter topInCategoriesAdapter;
    PeopleAreAlsoLookingForAdapter peopleAreAlsoLookingForAdapter;
    BestSellingAdapter bestSellingAdapter;
    BannerAdapter bannerAdapter;

    ApiInterface apiInterface;
    //public static ApiResponseModel apiResponseData;
    public static ApiResponseModel apiResponseData;
    VisitedProductResponse visitedProductResponse;
    BannerResponse bannerResponse;

    ProgressDialog progressDialog;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    String person_name, person_email;
    Uri person_picture;

    TextView errorTextView;
    ProgressBar bannerProgressBar, progressBar, bestSellingProgressBar, sellProductProgressBar,
            featureProductProgressBar, topInCategoriesProgressBar, peopleAreAlsoLookingForProgressBar;

    String name, email, phone;

    SessionManagement sessionManagement;
    public  static UserDetailsResponse userDetailsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();


        fetchLatestProduct();
        fetchBestSellingProduct();
        fetchSellProduct();
        fetchFeatureProduct();
        fetchTopInCategoriesProduct();
        fetchPeopleAreLookingAlsoForProduct();
        //ImageSlider();
        setListener();
        getBanner();

        System.out.println("User Phone: "+sessionManagement.getSessionModel().getUserPhone());

        //updateHeaderFromRegister();

        RetrofitClientWithHeader.getRetrofitClient(this).getUserDetails().enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                if (response.isSuccessful()){
                    userDetailsResponse = response.body();

                    NavigationView navigationView = findViewById(R.id.navigation_view);
                    View headerView = navigationView.getHeaderView(0);

                    TextView navUserName = headerView.findViewById(R.id.user_name);
                    TextView navUserEmail = headerView.findViewById(R.id.user_email);
                    TextView navUserNumber = headerView.findViewById(R.id.user_phone);

                    try {
                        navUserName.setText(userDetailsResponse.user.name);
                        navUserEmail.setText(userDetailsResponse.user.email);
                        navUserNumber.setText(userDetailsResponse.user.phone);
                    }
                    catch (Exception e){

                    }
                  }
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);

                switch (id){
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        break;

                    case R.id.nav_categories:
                        startActivity(new Intent(getApplicationContext(), AllCategoryActivity.class));
                        break;

                    case R.id.nav_cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        break;

                    case R.id.nav_order:
                        startActivity(new Intent(getApplicationContext(), OrderActivity.class));
                        break;

                    case R.id.nav_favourite:
                        startActivity(new Intent(getApplicationContext(), WishlistActivity.class));
                        //Toast.makeText(MainActivity.this, "Favourite", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_logout:
                        signOut();
                        break;

                    /*case R.id.nav_share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT,"Check this application");
                        intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.whatsapp");
                        startActivity(Intent.createChooser(intent,"Share Via"));
                        break;*/

                    case R.id.nav_contact:
                        startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));
                        //overridePendingTransition(0,0);
                        break;

                    /*case R.id.nav_about_us:
                        startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                        break;*/

                    default:
                        return true;
                }
                return true;
            }
        });



    }

    private void setListener(){
        all_category_text_view.setOnClickListener(this);
        top_in_categories_more_product.setOnClickListener(this);
        more_product.setOnClickListener(this);
    }

    private void initialization() {

        progressBar = findViewById(R.id.progressBar);
        bannerProgressBar = findViewById(R.id.bannerProgressBar);
        bestSellingProgressBar = findViewById(R.id.bestSellingProgressBar);
        sellProductProgressBar = findViewById(R.id.sellProductProgressBar);
        featureProductProgressBar = findViewById(R.id.featureProductProgressBar);
        topInCategoriesProgressBar = findViewById(R.id.topInCategoriesProgressBar);
        peopleAreAlsoLookingForProgressBar = findViewById(R.id.peopleAreAlsoLookingForProgressBar);


        errorTextView = findViewById(R.id.errorTextView);

        apiInterface = RetrofitClient.getRetrofitClient();
        progressDialog = new ProgressDialog(this);

        toolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        //bottomNavigationView = findViewById(R.id.bottom_nav);

        imageModelList = new ArrayList<>();

        all_category_text_view = findViewById(R.id.all_category);
        top_in_categories_more_product = findViewById(R.id.top_in_categories_more_product);
        more_product = findViewById(R.id.more_product);

        bannerRecyclerView = findViewById(R.id.bannerRecyclerView);
        featureRecyclerView = findViewById(R.id.featureRecyclerView);
        sellProductRecyclerView = findViewById(R.id.sellProductRecyclerview);
        topInCategoriesRecyclerView = findViewById(R.id.topCategoriesRecyclerView);
        peoplesAreAlsoLookingForRecyclerView = findViewById(R.id.peoplesAreAlsoLookingForRecyclerView);
        latestProductRecyclerView = findViewById(R.id.latest_product_Recyclerview);
        bestSellingRecyclerView = findViewById(R.id.bestSellingRecyclerview);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null){
            person_name = acct.getDisplayName();
            person_email = acct.getEmail();
            person_picture = acct.getPhotoUrl();

            updateHeader();

        }


        sessionManagement = new SessionManagement(this);


    }

    private void updateHeaderFromRegister(){



    }

    /*Update navigation header*/
    private void updateHeader(){

        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);

        TextView navUserName = headerView.findViewById(R.id.user_name);
        TextView navUserEmail = headerView.findViewById(R.id.user_email);
        ImageView navUserImage = headerView.findViewById(R.id.nav_user_photo);

        navUserName.setText(person_name);
        navUserEmail.setText(person_email);

        Glide.with(this).load(person_picture).into(navUserImage);
    }

    private void signOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                showToast("Logout");
                sessionManagement.removeLoginSession();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void getBanner() {

        bannerProgressBar.setVisibility(View.VISIBLE);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        bannerRecyclerView.setLayoutManager(layoutManager);

        RetrofitClient.getRetrofitClient().getBanner().enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                if (response.body() != null){
                    bannerProgressBar.setVisibility(View.INVISIBLE);
                    bannerResponse = response.body();
                    bannerAdapter = new BannerAdapter(MainActivity.this, bannerResponse);
                    bannerRecyclerView.setAdapter(bannerAdapter);

                    LinearSnapHelper snapHelper = new LinearSnapHelper();
                    snapHelper.attachToRecyclerView(bannerRecyclerView);

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (layoutManager.findLastCompletelyVisibleItemPosition() < bannerAdapter.getItemCount() -1){
                                layoutManager.smoothScrollToPosition(bannerRecyclerView, new RecyclerView.State(), layoutManager.findLastCompletelyVisibleItemPosition() + 1);
                            }else if (layoutManager.findLastCompletelyVisibleItemPosition() < bannerAdapter.getItemCount() -1){
                                layoutManager.smoothScrollToPosition(bannerRecyclerView, new RecyclerView.State(), 0);
                            }
                        }
                    },0, 3000);


                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {

            }
        });
    }

    private void fetchPeopleAreLookingAlsoForProduct() {

        peopleAreAlsoLookingForProgressBar.setVisibility(View.VISIBLE);

        peoplesAreAlsoLookingForRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        apiInterface.getSuggestion().enqueue(new Callback<VisitedProductResponse>() {
            @Override
            public void onResponse(Call<VisitedProductResponse> call, Response<VisitedProductResponse> response) {

                //progressBar.setVisibility(View.VISIBLE);

                if (response.body() != null){
                    peopleAreAlsoLookingForProgressBar.setVisibility(View.GONE);
                    visitedProductResponse = response.body();
                    peopleAreAlsoLookingForAdapter = new PeopleAreAlsoLookingForAdapter(MainActivity.this, visitedProductResponse);
                    peoplesAreAlsoLookingForRecyclerView.setAdapter(peopleAreAlsoLookingForAdapter);
                }
            }

            @Override
            public void onFailure(Call<VisitedProductResponse> call, Throwable t) {
                /*progressBar.setVisibility(View.GONE);
                errorTextView.setText("Something went wrong try again");*/
            }
        });
    }

    private void fetchTopInCategoriesProduct() {

        topInCategoriesProgressBar.setVisibility(View.VISIBLE);
        topInCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        apiInterface.getTopInCategories(1).enqueue(new Callback<ApiResponseModel>() {
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {

                if (response.body() != null){
                    topInCategoriesProgressBar.setVisibility(View.INVISIBLE);
                    apiResponseData = response.body();
                    topInCategoriesAdapter = new TopInCategoriesAdapter(MainActivity.this, apiResponseData);
                    topInCategoriesRecyclerView.setAdapter(topInCategoriesAdapter);
                }
            }

            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {

            }
        });
    }

    private void fetchFeatureProduct() {

        featureProductProgressBar.setVisibility(View.VISIBLE);

        featureRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        apiInterface.getFeature().enqueue(new Callback<ApiResponseModel>() {
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                if (response.body() != null){
                    featureProductProgressBar.setVisibility(View.GONE);
                    apiResponseData = response.body();
                    featureAdapter = new FeatureAdapter(MainActivity.this, apiResponseData);
                    featureRecyclerView.setAdapter(featureAdapter);
                }
            }

            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {

            }
        });

    }

    private void fetchSellProduct() {

        sellProductProgressBar.setVisibility(View.VISIBLE);

        sellProductProgressBar.setVisibility(View.VISIBLE);
        sellProductRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        apiInterface.getSale().enqueue(new Callback<ApiResponseModel>() {
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                if (response.body() != null){
                    sellProductProgressBar.setVisibility(View.INVISIBLE);
                    sellProductProgressBar.setVisibility(View.GONE);
                    apiResponseData = response.body();
                    sellProductAdapter = new SellProductAdapter(MainActivity.this, apiResponseData);
                    sellProductRecyclerView.setAdapter(sellProductAdapter);
                }
            }

            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {

            }
        });
    }

    // best sell RecyclerView
    private void fetchBestSellingProduct() {

        bestSellingProgressBar.setVisibility(View.VISIBLE);

        bestSellingRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        apiInterface.getBestSelling().enqueue(new Callback<ApiResponseModel>() {
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                if (response.body() != null){
                    bestSellingProgressBar.setVisibility(View.INVISIBLE);
                    apiResponseData = response.body();
                    bestSellingAdapter = new BestSellingAdapter(MainActivity.this, apiResponseData);
                    bestSellingRecyclerView.setAdapter(bestSellingAdapter);
                }
            }

            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {

            }
        });

    }

    // Latest Product RecyclerView

    private void fetchLatestProduct() {

        progressBar.setVisibility(View.VISIBLE);

        latestProductRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        apiInterface.getLatest().enqueue(new Callback<ApiResponseModel>() {
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                if (response.body() != null){
                    progressBar.setVisibility(View.INVISIBLE);
                    apiResponseData = response.body();
                    latestProductAdapter = new LatestProductAdapter(MainActivity.this, apiResponseData);
                    latestProductRecyclerView.setAdapter(latestProductAdapter);
                }
            }

            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {

            }
        });

    }


    private void loginUser() {

    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 >System.currentTimeMillis()){
            finish();
            super.onBackPressed();
            return;
        }
        else {
            backToast = Toast.makeText(this, "Tap again to exit...", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.all_category:
                startActivity(new Intent(getApplicationContext(), AllCategoryActivity.class));
                break;

            case R.id.more_product:
                startActivity(new Intent(getApplicationContext(), TopCategoryActivity.class));
                break;

            default:
                return;
        }
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