package com.example.huaweilib.api;

import com.example.huaweilib.responses.DetectResponse;
import com.example.huaweilib.responses.StoreResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HuaweiAPI {
    @Multipart
    @POST("store/")
    Call<StoreResponse> storeImage(
            @Part("collectionid") RequestBody collectionid,
            @Part("facename") RequestBody facename,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("detect/")
    Call<DetectResponse> matchImage(
            @Part MultipartBody.Part file,
            @Part("collectionid") RequestBody collectionid
    );
}
