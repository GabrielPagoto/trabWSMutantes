package com.example.trabwsmutantes.ApiMutants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retorfit;

    public RetrofitConfig() {
        this.retorfit  = new Retrofit.Builder().
        baseUrl("https://d5c7-138-118-169-27.sa.ngrok.io/")

                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public MutantsService getMutantService(){
        return this.retorfit.create(MutantsService.class);
    }
    public LoginService getLoginService(){
        return this.retorfit.create(LoginService.class);
    }
}
