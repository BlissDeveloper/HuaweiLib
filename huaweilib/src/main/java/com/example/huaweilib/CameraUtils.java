package com.example.huaweilib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
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


    public Bitmap scaleBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(270);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 960, 1280, true);
        return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,   true);
    }
}
