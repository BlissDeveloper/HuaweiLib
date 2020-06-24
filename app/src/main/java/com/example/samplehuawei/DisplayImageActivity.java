package com.example.samplehuawei;

import android.app.AppComponentFactory;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.huaweilib.CameraUtils;
import com.example.huaweilib.ImageData;

public class DisplayImageActivity extends AppCompatActivity {
    private CameraUtils cameraUtils;

    private ImageView imageView;

    private ImageData image;
    private byte[] imageData;
    private Bitmap bitmap;

    private ImageData img;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        img = ImageData.getInstance();

        cameraUtils = new CameraUtils(this);

        imageView = findViewById(R.id.imageViewDisplay);

        bitmap = cameraUtils.convertToBitmap(img.getImageData());

        imageView.setImageBitmap(bitmap);

    }
}
