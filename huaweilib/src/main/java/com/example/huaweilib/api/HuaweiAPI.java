package com.example.huaweilib.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HuaweiAPI {
    @Multipart
    @POST("store/")
    Call<String> storeImage(
            @Part("collectionid") RequestBody collectionid,
            @Part("facename") RequestBody facename,
            @Part MultipartBody.Part file);
}
