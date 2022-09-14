package com.example.trabwsmutantes.ApiMutants;

import com.example.trabwsmutantes.Model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginService {
    @GET("User")
    Call<User> getUser(@Query("Email") String Email, @Query("Password") String Password);
}
