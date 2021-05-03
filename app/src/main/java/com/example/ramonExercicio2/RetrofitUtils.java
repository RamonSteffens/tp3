package com.example.ramonExercicio2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    public static Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://guisfco-online-shopping-api.herokuapp.com/api/online-shopping/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

}
