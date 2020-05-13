package com.example.absensiguruprivate.rest;

import com.example.absensiguruprivate.model.AbsensiItem_prawesti;
import com.example.absensiguruprivate.model.GuruItem_prawesti;
import com.example.absensiguruprivate.model.User_prawesti;

import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

import retrofit2.http.POST;

public interface ApiInterface_prawesti {
    @POST("LoginAdmin")
    Call<ResponseBody> loginAdmin(@Body User_prawesti user);

    @POST("LoginGuru")
    Call<ResponseBody> loginGuru(@Body User_prawesti user);

    @GET("Guru")
    Call<List<GuruItem_prawesti>> getGuru();

    @GET("Guru")
    Call<List<GuruItem_prawesti>> getGuruByUsername(
            @Query("username") String username
    );

    @GET("AbsenGuru")
    Call<List<AbsensiItem_prawesti>> getAbsenByUsername(
            @Query("username") String username
    );

    @POST("AbsenGuru")
    Call<ResponseBody> absenGuru(@Body AbsensiItem_prawesti absen);

    @Multipart
    @POST("Guru")
    Call<ResponseBody> tambahGuru(
            @Part MultipartBody.Part photo,
            @PartMap Map<String, RequestBody> text);

}
