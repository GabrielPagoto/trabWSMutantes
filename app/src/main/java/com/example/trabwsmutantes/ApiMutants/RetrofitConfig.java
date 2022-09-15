package com.example.trabwsmutantes.ApiMutants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retorfit;

    public RetrofitConfig() {
        this.retorfit  = new Retrofit.Builder().
        baseUrl("https://7a3b-2804-7f4-378e-dc86-a49f-d767-d316-473c.sa.ngrok.io/")
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
