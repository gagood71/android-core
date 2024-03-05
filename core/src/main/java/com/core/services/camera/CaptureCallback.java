package com.core.services.camera;

import android.content.Intent;

import java.io.File;

public interface CaptureCallback {
    void onCapture(Intent intent, File file, int requestCode);
}
