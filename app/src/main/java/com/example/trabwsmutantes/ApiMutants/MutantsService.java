package com.example.trabwsmutantes.ApiMutants;

import com.example.trabwsmutantes.Model.Mutant;

import java.util.List;

import javax.xml.namespace.QName;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MutantsService {
    Call<Mutant> getMutant();

    @GET("Mutants")
    Call<List<Mutant>> getMutantList(@Query("id") int id);

    @GET("Mutant")
    Call<Mutant> getMutant(@Query("id") int id);


    @Multipart
    @POST("Mutant")
    Call<String> uploadAttachment(@Part MultipartBody.Part filePart,
                          @Part("name") RequestBody  name,
                          @Part("abilities_one") RequestBody  abilities_one,
                          @Part("abilities_two") RequestBody  abilities_two,
                          @Part("abilities_tree") RequestBody  abilities_tree,
                          @Part("professorId") RequestBody professorId

    );

    @DELETE("Mutant")
    Call<Mutant> deleteMutant(@Query("id") int id);
}
