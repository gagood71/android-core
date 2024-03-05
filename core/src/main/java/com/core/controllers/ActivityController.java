package com.core.controllers;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.core.activities.v1.CompatActivity;
import com.core.configuration.Configuration;
import com.core.services.file.FileCreateCallback;
import com.core.services.file.FileService;
import com.core.utils.CropImageCallback;
import com.core.utils.CropImageUtil;

import java.io.File;
import java.util.Arrays;

@SuppressWarnings("rawtypes")
public abstract class ActivityController<T extends CompatActivity>
        extends ViewController<T> {
    private CropImageCallback cropImageCallback;

    private Uri cacheUri;
    private Uri cacheCropUri;

    public ActivityController() {
        super();
    }

    @Override
    public void setContext(T object) {
        super.setContext(object);

        cropImageCallback = (intent, requestCode) -> context.startActivityForResult(intent, requestCode);

        cacheUri = CropImageUtil.getCacheUri(context);
        cacheCropUri = CropImageUtil.getCacheCropUri(context);
    }

    protected boolean isPermissionsGranted(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        boolean isGranted = true;

        int i = 0;
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                isGranted = false;

                Log.e(tag, REQUEST_CODE + requestCode + PERMISSION_REJECT + permissions[i]);
                break;
            } else {
                Log.e(tag, REQUEST_CODE + requestCode + PERMISSION_AGREE + permissions[i]);
            }

            i++;
        }

        return isGranted;
    }

    /**
     * Activity > onActivityResult
     */
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {
        Log.e(tag, REQUEST_CODE + requestCode);
        Log.e(tag, RESULT_CODE + resultCode);

        if (requestCode == Configuration.Permission.CAMERA_CAPTURE_PERMISSIONS ||
                requestCode == Configuration.Permission.CAMERA_GALLERY_PERMISSIONS) {
            Uri inputUri;
            Uri outputUri;

            boolean isWritable = false;

            if (data != null && data.getData() != null) {
                inputUri = data.getData();
            } else {
                inputUri = cacheUri;

                isWritable = true;
            }

            if (isWritable) {
                outputUri = inputUri;
            } else {
                outputUri = cacheUri;
            }

            FileService.save(context,
                    new FileCreateCallback() {
                        @Override
                        public void onCreate(File outputFile, Uri outputFileUri) {
                            Log.d(getClass().getName(), outputFile + "建立完成");

                            CropImageUtil.cropImage(
                                    context,
                                    cropImageCallback,
                                    outputUri,
                                    cacheCropUri,
                                    160,
                                    160,
                                    Configuration.Permission.CAMERA_CROP_PERMISSIONS
                            );
                        }

                        @Override
                        public void onFailed(String message) {
                            context.showCenterToast(message);
                        }
                    },
                    inputUri,
                    outputUri
            );
        } else if (requestCode == Configuration.Permission.CAMERA_CROP_PERMISSIONS) {
            Uri uri;

            if (data != null && data.getData() != null) {
                uri = data.getData();
            } else {
                uri = cacheCropUri;
            }

            context.getContentResolver().delete(
                    uri,
                    null,
                    null
            );
        }
    }

    /**
     * Activity > onRequestPermissionsResult
     */
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.e(tag, REQUEST_CODE + requestCode);
        Log.e(tag, PERMISSIONS_REQUEST + Arrays.toString(permissions));
    }

    /**
     * Activity > onStart（多次觸發）
     */
    public abstract void onStart();

    /**
     * Activity > onPause
     */
    public abstract void onPause();

    /**
     * Activity > onDestroy
     */
    public abstract void onDestroy();
}
