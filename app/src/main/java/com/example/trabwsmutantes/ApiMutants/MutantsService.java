package com.example.trabwsmutantes.ApiMutants;

import com.example.trabwsmutantes.Model.Mutant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MutantsService {
    Call<Mutant> getMutant();

    @GET("Mutants")
    Call<List<Mutant>> getMutantList(@Query("id") int id);

    @GET("Mutant")
    Call<Mutant> getMutant(@Query("id") int id);

    @DELETE("Mutant")
    Call<Mutant> deleteMutant(@Query("id") int id);
}
