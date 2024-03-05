package com.core.services.file;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.core.activities.ActivityResultCallback;
import com.core.activities.v1.CompatActivity;
import com.core.configuration.Configuration;
import com.core.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    // 16 KB
    private static final byte[] BYTES = new byte[16 * 1024];

    @SuppressLint("Recycle")
    public static void save(Context context,
                            FileCreateCallback callback,
                            Uri inputUri,
                            Uri outputUri) {
        ContentResolver resolver = context.getContentResolver();

        try {
            InputStream inputStream = resolver.openInputStream(inputUri);
            AssetFileDescriptor fileDescriptor = resolver.openAssetFileDescriptor(outputUri, "rw");
            FileOutputStream outputStream;

            if (fileDescriptor != null) {
                outputStream = fileDescriptor.createOutputStream();

                int length;

                if (inputStream != null) {
                    while ((length = inputStream.read(BYTES)) > 0) {
                        outputStream.write(BYTES, 0, length);
                    }

                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();
                }
            }

            callback.onCreate(FileUtil.getFile(outputUri.toString()), outputUri);
        } catch (IOException e) {
            e.printStackTrace();

            callback.onFailed(e.getMessage());
        }
    }

    public static void save(Context context,
                            FileCreateCallback callback,
                            String fileName) {
        String cacheFile = FileUtil.getCacheFile(context, fileName);

        if (!TextUtils.isEmpty(cacheFile)) {
            File outputFile = new File(cacheFile);
            Uri outputFileUri = FileProvider.getUriForFile(
                    context,
                    "com.core.provider",
                    outputFile
            );

            callback.onCreate(outputFile, outputFileUri);
        } else {
            callback.onFailed("Couldn't find " + fileName + ".");
        }
    }

    public static void register(CompatActivity<?> activity,
                                ActivityResultCallback callback) {
        String[] permissions = getPermissions();

        if (!activity.isPermissionsGranted(permissions)) {
            activity.registerPermissions(
                    permissions,
                    Configuration.Permission.FILE_PERMISSIONS
            );
        } else {
            callback.granted();
        }
    }

    public static String[] getPermissions() {
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        String[] permissionArray = new String[permissions.size()];
        permissions.toArray(permissionArray);

        return permissionArray;
    }
}
