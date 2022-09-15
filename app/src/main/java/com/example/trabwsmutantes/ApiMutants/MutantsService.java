package com.example.trabwsmutantes.ApiMutants;

import com.example.trabwsmutantes.Model.Mutant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MutantsService {
    Call<Mutant> getMutant();
    @GET("Mutants/{id}")
    Call<List<Mutant>> getListMutants(@Path("id") int id);
}
