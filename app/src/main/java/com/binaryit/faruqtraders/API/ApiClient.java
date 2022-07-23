package com.binaryit.faruqtraders.API;

import com.binaryit.faruqtraders.Model.BestSellingModel;
import com.binaryit.faruqtraders.Model.FeatureModel;
import com.binaryit.faruqtraders.Model.LatestProductModel;
import com.binaryit.faruqtraders.Model.SellProductModel;
import com.binaryit.faruqtraders.Model.TopInCategoriesModel;
import com.binaryit.faruqtraders.Request.LoginRequest;
import com.binaryit.faruqtraders.Response.LoginResponse;
import com.binaryit.faruqtraders.Response.UserRegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiClient {

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
    @POST("/auth/user/login/")
    Call<UserRegisterResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_name") String device_name
            );

    @FormUrlEncoded
    @POST("/auth/user/login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);


    /*Show the latest product Details*/

    @GET("here will be last link")
    Call<List<LatestProductModel>> getLatestProduct();


    /*Show the Best Selling product Details*/
    @GET("here will be last link")
    Call<List<BestSellingModel>> getBestSellingProduct();


    /*Show the Sale product Details*/
    @GET("here will be last link")
    Call<List<SellProductModel>> getSaleProduct();


    /*Show the feature product Details*/
    @GET("/product/featured")
    Call<List<FeatureModel>> getFeatureProduct();


    /*Show the top in categories product Details*/
    @GET("here will b last link")
    Call<List<TopInCategoriesModel>> getTopInCategoriesProduct();


}
