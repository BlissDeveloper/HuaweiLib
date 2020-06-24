package com.example.huaweilib;

import android.media.Image;

import java.io.Serializable;

public class ImageData {
    private byte[] imageData;

    private static final ImageData instance = new ImageData();

    public static ImageData getInstance() {
        return instance;
    }

    public ImageData() {

    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
