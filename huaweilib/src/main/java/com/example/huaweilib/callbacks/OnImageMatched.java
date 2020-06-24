package com.example.huaweilib.callbacks;

import com.example.huaweilib.responses.DetectResponse;

public interface OnImageMatched {
    void onSuccess(DetectResponse detectResponse);

    void onMismatch(DetectResponse detectResponse);

    void onError(String message);
}
