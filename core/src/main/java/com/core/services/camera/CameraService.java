package com.core.services.camera;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.core.activities.ActivityResultCallback;
import com.core.activities.v1.CompatActivity;
import com.core.configuration.Configuration;
import com.core.services.file.FileCreateCallback;
import com.core.services.file.FileService;
import com.core.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CameraService {
    public static void imageCapture(CompatActivity<?> activity,
                                    CaptureCallback callback) {
        FileService.save(activity,
                new FileCreateCallback() {
                    @Override
                    public void onCreate(File outputFile, Uri outputFileUri) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                        callback.onCapture(
                                intent,
                                outputFile,
                                Configuration.Permission.CAMERA_CAPTURE_PERMISSIONS
                        );
                    }

                    @Override
                    public void onFailed(String message) {
                        activity.showCenterToast(message);
                    }
                },
                FileUtil.getTemporaryJPG()
        );
    }

    public static void imageGalley(GalleryCallback callback) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        callback.onPick(intent, Configuration.Permission.CAMERA_GALLERY_PERMISSIONS);
    }

    public static void register(CompatActivity<?> activity,
                                ActivityResultCallback callback) {
        String[] permissions = getPermissions();

        if (!activity.isPermissionsGranted(permissions)) {
            activity.registerPermissions(
                    permissions,
                    Configuration.Permission.CAMERA_PERMISSIONS
            );
        } else {
            callback.granted();
        }
    }

    public static String[] getPermissions() {
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        String[] permissionArray = new String[permissions.size()];
        permissions.toArray(permissionArray);

        return permissionArray;
    }
}
