package com.example.trabwsmutantes.ApiMutants;

import com.example.trabwsmutantes.Model.Mutante;

import retrofit2.Call;

public interface MutantsService {
    Call<Mutante> getMutante();
}
