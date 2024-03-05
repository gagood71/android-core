package com.core.services.camera;

import android.content.Intent;

public interface GalleryCallback {
    void onPick(Intent intent, int requestCode);
}
