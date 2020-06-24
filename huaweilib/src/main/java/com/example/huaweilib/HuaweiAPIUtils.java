package com.example.huaweilib;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;

import com.example.huaweilib.api.HuaweiAPI;
import com.example.huaweilib.callbacks.OnImageMatched;
import com.example.huaweilib.callbacks.OnImageStored;
import com.example.huaweilib.responses.DetectResponse;
import com.example.huaweilib.responses.StoreResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HuaweiAPIUtils {
    private String baseUrl;

    private Context context;

    private Gson gson;
    private Retrofit retrofit;
    private HuaweiAPI huaweiAPI;

    private double passingScore = 0.85;

    /*
    CALLBACKS
     */
    private OnImageMatched onImageMatched;
    private OnImageStored onImageStored;

    public HuaweiAPIUtils(Context context) {
        this.context = context;

        baseUrl = context.getString(R.string.huawei_core_url);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(15, TimeUnit.SECONDS) // connect timeout
                .writeTimeout(15, TimeUnit.SECONDS) // write timeout
                .readTimeout(15, TimeUnit.SECONDS) // read timeout
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        huaweiAPI = retrofit.create(HuaweiAPI.class);

        /*
        CALLBACKS
         */


    }

    public void storeImage(File file) {
        RequestBody collectionId = RequestBody.create(okhttp3.MultipartBody.FORM, HuaweiConstants.COLLECTION_ID);
        RequestBody faceName = RequestBody.create(okhttp3.MultipartBody.FORM, "sample-name-avery");
        RequestBody imageFile = RequestBody.create(file, MediaType.parse("image/*"));

        MultipartBody.Part mFile = MultipartBody.Part.createFormData("file", "nani", imageFile);

        Call<StoreResponse> storeImg = huaweiAPI.storeImage(collectionId, faceName, mFile);

        storeImg.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                String message = "";
                if (response.isSuccessful()) {
                    String status = response.body().getStatus().toUpperCase();
                    if (status.equals(HuaweiConstants.SUCCESS)) {
                        onImageStored.onSuccess(response.body());
                    } else {
                        message = "Error code: " + response.code();
                        onImageStored.onError(message);
                    }
                } else {
                    message = "Error code: " + response.code();
                    onImageStored.onError(message);
                }
            }

            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                String message = t.getMessage();
                onImageStored.onError(message);
            }
        });

    }

    public void matchImage(File file) {
        RequestBody collectionId = RequestBody.create(okhttp3.MultipartBody.FORM, HuaweiConstants.COLLECTION_ID);
        RequestBody imageFile = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part mFile = MultipartBody.Part.createFormData("file", "nani", imageFile);

        Call<DetectResponse> matchImage = huaweiAPI.matchImage(mFile, collectionId);

        matchImage.enqueue(new Callback<DetectResponse>() {
            @Override
            public void onResponse(Call<DetectResponse> call, Response<DetectResponse> response) {
                String message = "";
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equals(HuaweiConstants.SUCCESS)) {
                        double score = Double.parseDouble(response.body().getSimilarity());
                        if (score > passingScore) {
                            onImageMatched.onSuccess(response.body());
                        } else {
                            onImageMatched.onMismatch(response.body());
                        }
                    } else {
                        message = "Error code: " + response.code();
                        onImageMatched.onError(message);
                    }
                } else {
                    message = "Error code: " + response.code();
                    onImageMatched.onError(message);
                }
            }

            @Override
            public void onFailure(Call<DetectResponse> call, Throwable t) {
                String message = t.getMessage();
                onImageMatched.onError(message);
            }
        });

    }

    public void setOnImageMatched(OnImageMatched onImageMatched) {
        this.onImageMatched = onImageMatched;
    }

    public void setOnImageStored(OnImageStored onImageStored) {
        this.onImageStored = onImageStored;
    }
}
