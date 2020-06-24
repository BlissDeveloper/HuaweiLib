package com.example.huaweilib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.hardware.camera2.*;

public class CameraUtils {
    Context context;

    public CameraUtils(Context context) {
        this.context = context;
    }

    public Bitmap convertToBitmap(byte[] byteArray) {
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bmp;
    }
}
