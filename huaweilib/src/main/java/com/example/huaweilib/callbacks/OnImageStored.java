package com.example.huaweilib.callbacks;

import com.example.huaweilib.responses.StoreResponse;

public interface OnImageStored {
    void onSuccess(StoreResponse storeResponse);

    void onError(String message);
}
