package com.binaryit.faruqtraders.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientForDelivery {

    public static final String BASE_URL= "https://faruqtraders.com/api/v1/";
    public static Retrofit retrofit = null;

    public static ApiInterface getRetrofitClient(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }

}
