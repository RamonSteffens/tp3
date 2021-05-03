package com.example.ramonExercicio2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerService {

    @POST("customer")
    Call<Customer> adicionar(@Body Customer customer);

    @GET("customer")
    Call<List<Customer>> listarCustomer();

    @DELETE("customer/{id}")
    Call<Void> deletar(@Path("id") long id);

    @PUT("customer/{id}")
    Call<Customer> atualizar(@Path("id") long id, @Body Customer customer);

}
