package com.abuunity.latihanfragmant.api;

import com.abuunity.latihanfragmant.pojo.old_ResponseMahasiswa;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface old_Api {
    @GET("old_Api.php?apicall=getallmahasiswa")
    Call<old_ResponseMahasiswa> getAllMahasiswa();

    @POST("old_Api.php?apicall=savemahasiswa")
    @FormUrlEncoded
    Call<old_ResponseMahasiswa> saveMahasiswa(@Field("nim") String nim,
                                              @Field("nama") String nama,
                                              @Field("prodi") String prodi);
    @POST("old_Api.php?apicall=updatemahasiswa")
    @FormUrlEncoded
    Call<old_ResponseMahasiswa> editMahasiswa(@Field("id") String id,
                                              @Field("nim") String nim,
                                              @Field("nama") String nama,
                                              @Field("prodi") String prodi);
    @POST("old_Api.php?apicall=deletemahasiswa")
    @FormUrlEncoded
    Call<old_ResponseMahasiswa> deleteMahasiswa(@Field("id") String id);

}
