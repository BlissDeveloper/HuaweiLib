package com.example.samplehuawei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.icu.util.UniversalTimeScale;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.huaweilib.HuaweiConstants;
import com.example.huaweilib.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class HuaweiCameraActivity extends AppCompatActivity {
    private Camera mCamera;
    private HuaweiCamPreview huaweiCamPreview;

    private FrameLayout frameLayout;
    private ImageButton buttonCapture;

    private final int REQ_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huawei_camera);

        mCamera = getCameraInstance();

        huaweiCamPreview = new HuaweiCamPreview(this, mCamera);
        frameLayout = findViewById(R.id.frameLayout);
        frameLayout.addView(huaweiCamPreview);

        initViews();

        if (!arePermissionsGranted()) {
            requestPermissions();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQ_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(HuaweiConstants.HUAWEI_TAG, "Permission granted!");
                }
                break;
        }
    }

    public void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                REQ_CODE);
    }

    public boolean arePermissionsGranted() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void initViews() {
        final Camera.PictureCallback mPicture = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                if (pictureFile == null) {
                    Log.d(HuaweiConstants.HUAWEI_TAG, "Error creating media file, check storage permissions");
                    return;
                }

                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                } catch (FileNotFoundException e) {
                    Log.d(HuaweiConstants.HUAWEI_TAG, "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d(HuaweiConstants.HUAWEI_TAG, "Error accessing file: " + e.getMessage());
                }
            }
        };

        buttonCapture = findViewById(R.id.buttonCapture);

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(HuaweiConstants.HUAWEI_TAG, "Clicked!");
                mCamera.takePicture(null, null, mPicture);
            }
        });
    }

    public File getOutputMediaFile() {
        String state = Environment
    }


    public Camera getCameraInstance() {
        Camera camera = null;

        try {
            camera = Camera.open(1);
        } catch (Exception e) {

        }
        return camera;
    }
}