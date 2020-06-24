package com.example.samplehuawei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.huaweilib.ImageData;

import java.io.Serializable;

public class CameraActivity extends HuaweiCameraActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onImageTaken(byte[] data) {
        ImageData imageData = ImageData.getInstance();
        imageData.setImageData(data);

        startActivity(new Intent(this, DisplayImageActivity.class));
    }
}