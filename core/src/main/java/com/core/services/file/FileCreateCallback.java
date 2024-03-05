package com.core.services.file;

import android.net.Uri;

import java.io.File;

public interface FileCreateCallback {
    void onCreate(File outputFile, Uri outputFileUri);

    void onFailed(String message);
}
