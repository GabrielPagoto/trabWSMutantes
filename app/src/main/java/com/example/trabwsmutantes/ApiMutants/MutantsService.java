package com.example.trabwsmutantes.ApiMutants;

import com.example.trabwsmutantes.Model.Dashboard;
import com.example.trabwsmutantes.Model.Mutant;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MutantsService {
    Call<Mutant> getMutant();

    @GET("Mutants")
    Call<List<Mutant>> getMutantList(@Query("id") int id);

    @GET("Mutant")
    Call<Mutant> getMutant(@Query("id") int id);

    @GET("Mutants/Dashboard")
    Call<Dashboard> getMutantDashboard(@Query("id") int id);

    @GET("Mutants/search")
    Call<List<Mutant>> getMutantListHabilities(@Query("id") int id, @Query("hab") String hab);

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

    @Multipart
    @PUT("Mutant")
    Call<String> createMutant(@Query("id") int id,
                                  @Part MultipartBody.Part filePart,
                                  @Part("name") RequestBody  name,
                                  @Part("abilities_one") RequestBody  abilities_one,
                                  @Part("abilities_two") RequestBody  abilities_two,
                                  @Part("abilities_tree") RequestBody  abilities_tree,
                                  @Part("professorId") RequestBody professorId

    );
}
