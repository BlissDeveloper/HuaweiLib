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
            @Part MultipartBody.Part file,
            @Part("collectionid") RequestBody collectionId,
            @Part("facename") RequestBody faceName);
}
