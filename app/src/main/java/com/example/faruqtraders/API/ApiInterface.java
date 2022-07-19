package com.example.faruqtraders.API;

import com.example.faruqtraders.Model.BestSellingModel;
import com.example.faruqtraders.Model.FeatureModel;
import com.example.faruqtraders.Model.LatestProductModel;
import com.example.faruqtraders.Model.SellProductModel;
import com.example.faruqtraders.Model.TopInCategoriesModel;
import com.example.faruqtraders.Request.LoginRequest;
import com.example.faruqtraders.Response.AddCartResponse;
import com.example.faruqtraders.Response.AddToCartPostModel;
import com.example.faruqtraders.Response.AddToCartResponse;
import com.example.faruqtraders.Response.ApiResponseModel;
import com.example.faruqtraders.Response.BannerResponse;
import com.example.faruqtraders.Response.CartResponseModel;
import com.example.faruqtraders.Response.CategoryResponseModel;
import com.example.faruqtraders.Response.DeliveryMethodResponse;
import com.example.faruqtraders.Response.EachProductResponse;
import com.example.faruqtraders.Response.FilterResponseModel;
import com.example.faruqtraders.Response.LoginResponse;
import com.example.faruqtraders.Response.ProductDetailsResponseModel;
import com.example.faruqtraders.Response.UserDetailsResponse;
import com.example.faruqtraders.Response.UserRegisterResponse;
import com.example.faruqtraders.Response.VisitedProductResponse;
import com.google.android.gms.common.api.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    /*Sign Up*/
    @FormUrlEncoded
    @POST("auth/user/registration")
    Call<UserRegisterResponse> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("password_confirmation") String confirmPassword,
            @Field("device_name") String device_name
            );

    @FormUrlEncoded
    @POST("auth/user/login")
    Call<UserRegisterResponse> userLogin(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("device_name") String device_name
            );


    /*Logout */

    /*@POST("auth/user/logout")
    Call<> logout();*/

    @GET("banners")
    Call<BannerResponse> getBanner();

    @GET("product/sellings")
    Call<ApiResponseModel> getBestSelling();

    @GET("product/featured")
    Call<ApiResponseModel> getFeature();

    @GET("product/sale")
    Call<ApiResponseModel> getSale();

    @GET("product/sale")
    Call<ApiResponseModel> getLatest();

    @GET("product/products")
    Call<ApiResponseModel> getTopInCategories(
            @Query("page") int page
    );


    /*Get all the categories*/
    @GET("category/index")
    Call<CategoryResponseModel> getCategories();


    /*Get people are also looking for*/
    @GET("product/visited")
    Call<VisitedProductResponse> getSuggestion();



    @GET("product/details/{slug}")
    Call<ProductDetailsResponseModel> getProductDetails(
            @Path("slug") String slug
    );


    @GET("product/products")
    Call<ApiResponseModel> getRelatedProduct(
            @Query("category") String Category
            );


    @GET("product/products")
    Call<ApiResponseModel> getCategoryWiseProduct(
            @Query("category") String category,
            @Query("page") int page
            );

    @POST("carts/add/{path}")
    Call<AddCartResponse> addToCart(
            @Path("path") String product_id,
            @Body AddToCartPostModel addToCartPostModel
    );



    @GET("carts")
    Call<CartResponseModel> getCartDetails();

    @POST("user/profile/update")
    Call<UserDetailsResponse> updateProfile(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("address") String address

    );

    @GET("user/dashboard")
    Call<UserDetailsResponse> getUserDetails();


    @GET("user/orders")
    Call<DeliveryMethodResponse> getOrder();

    @GET("user/order/details/")
    Call<DeliveryMethodResponse> getOrderDetails(
            @Query("order_id") String order_id
    );


    @GET("checkout/delivery/methods")
    Call<DeliveryMethodResponse> getDeliveryCharge();


    @FormUrlEncoded
    @POST("checkout/process")
    Call<DeliveryMethodResponse> placeOrder(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("delivery_method") String delivery_method,
            @Field("address") String address
    );

}
