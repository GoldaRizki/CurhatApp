package com.example.appcatatan.apiclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CatatanInterface {

    @GET("rest_api/")
    Call<List<Data>> getData(@Query("aksi") String aksi, @Query("id") int id);

    @FormUrlEncoded
    @POST("rest_api/")
    Call<Data> postData(@Field("aksi") String aksi, @Field("username") String username, @Field("judul") String judul, @Field("isi") String isi);

    @DELETE("rest_api/")
    Call<Data> hapusData(@Query("aksi") String aksi, @Query("id") int id, @Query("username") String username);


    @PUT("rest_api/")
    Call<Data> updateData(@Query("id") int id, @Query("judul") String judul, @Query("isi") String isi);

}
