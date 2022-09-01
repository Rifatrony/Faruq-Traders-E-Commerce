package com.binaryit.faruqtraders.API;

import com.binaryit.faruqtraders.Response.AddCartResponse;
import com.binaryit.faruqtraders.Response.AddToCartPostModel;
import com.binaryit.faruqtraders.Response.ApiResponseModel;
import com.binaryit.faruqtraders.Response.BannerResponse;
import com.binaryit.faruqtraders.Response.CartResponseModel;
import com.binaryit.faruqtraders.Response.CategoryResponseModel;
import com.binaryit.faruqtraders.Response.DeleteResponse;
import com.binaryit.faruqtraders.Response.DeliveryMethodResponse;
import com.binaryit.faruqtraders.Response.DetailsOrderResponse;
import com.binaryit.faruqtraders.Response.OrderDetailsResponse;
import com.binaryit.faruqtraders.Response.OrderResponse;
import com.binaryit.faruqtraders.Response.ProductDetailsResponseModel;
import com.binaryit.faruqtraders.Response.RegistrationResponse;
import com.binaryit.faruqtraders.Response.UserDetailsResponse;
import com.binaryit.faruqtraders.Response.UserRegisterResponse;
import com.binaryit.faruqtraders.Response.VisitedProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
            @Field("device_name") String device_name,
            @Field("otp") String otp
            );

    @FormUrlEncoded
    @POST("auth/user/registration")
    Call<RegistrationResponse> sendUserData(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("password_confirmation") String confirmPassword,
            @Field("device_name") String device_name,
            @Field("otp") String otp
    );

    @FormUrlEncoded
    @POST("auth/user/login")
    Call<UserRegisterResponse> userLogin(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("device_name") String device_name
            );

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

    @GET("product/products")
    Call<ApiResponseModel> searchProduct(
            @Query("search") String search
    );

    @POST("carts/add/{path}")
    Call<AddCartResponse> addToCart(
            @Path("path") String product_id,
            @Body AddToCartPostModel addToCartPostModel
    );

    @DELETE("carts/{path}/delete")
    Call<DeleteResponse> deleteAItem(
            @Path("path") String product_id
    );

    @DELETE("carts/clear")
    Call<DeleteResponse> ClearCart();

    @GET("carts")
    Call<CartResponseModel> getCartDetails();

    @GET("carts/{path}/increment")
    Call<CartResponseModel> incrementCart(
            @Path("path") String product_id
    );


    @GET("carts/{path}/decrement")
    Call<CartResponseModel> decrementCart(
            @Path("path") String product_id
    );


    @FormUrlEncoded
    @POST("user/profile/update")
    Call<DeleteResponse> updateProfile(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("address") String address

    );

    @GET("user/dashboard")
    Call<UserDetailsResponse> getUserDetails();


    @GET("user/orders")
    Call<OrderDetailsResponse> getOrder();

    @GET("user/order/details/{path}")
    Call<List<DetailsOrderResponse>> getOrderDetails(
            @Path("path") String order_id
    );


    @GET("checkout/delivery/methods")
    Call<List<DeliveryMethodResponse>> getDeliveryCharge();


    @FormUrlEncoded
    @POST("checkout/process")
    Call<OrderResponse> placeOrder(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("delivery_method") String delivery_method,
            @Field("address") String address
    );

}
