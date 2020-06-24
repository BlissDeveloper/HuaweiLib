package com.example.samplehuawei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.huaweilib.HuaweiAPIUtils;
import com.example.huaweilib.HuaweiConstants;
import com.example.huaweilib.ImageData;
import com.example.huaweilib.api.HuaweiAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;

public class CameraActivity extends HuaweiCameraActivity {
    private HuaweiAPIUtils huaweiAPIUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        huaweiAPIUtils = new HuaweiAPIUtils(this);
    }

    @Override
    public void onImageTaken(byte[] data) {
        try {
            File tempFile = File.createTempFile("avery-martin", "jpg", null);
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(data);

            huaweiAPIUtils.storeImage(tempFile);
        } catch (Exception e) {
            Log.e(HuaweiConstants.HUAWEI_TAG, e.getMessage());
        }
    }
}