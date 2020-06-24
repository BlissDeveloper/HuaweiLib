package com.example.huaweilib;

import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;

import com.example.huaweilib.api.HuaweiAPI;
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

    public HuaweiAPIUtils(Context context) {
        this.context = context;

        baseUrl = context.getString(R.string.huawei_core_url);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
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
        RequestBody collectionId = RequestBody.create(MediaType.parse("multipart/form-data"), HuaweiConstants.COLLECTION_ID);
        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestName = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());

        MultipartBody.Part requestImageFile = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<String> storeImg = huaweiAPI.storeImage(requestImageFile, collectionId, requestName);

        storeImg.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d(HuaweiConstants.HUAWEI_TAG, response.body());
                } else {
                    Log.e(HuaweiConstants.HUAWEI_TAG, "Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(HuaweiConstants.HUAWEI_TAG, t.getMessage());
            }
        });

    }
}
