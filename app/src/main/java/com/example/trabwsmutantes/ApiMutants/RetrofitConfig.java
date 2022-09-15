package com.example.trabwsmutantes.ApiMutants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retorfit;

    public RetrofitConfig() {
        this.retorfit  = new Retrofit.Builder().
        baseUrl("https://08b1-2804-7f4-378e-dc86-ed30-ec7c-e28e-1505.sa.ngrok.io/")
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
