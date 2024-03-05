package com.core.utils;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.core.activities.v1.CompatActivity;

import java.io.File;
import java.util.Objects;

public class CropImageUtil {
    public static void cropImage(CompatActivity<?> activity,
                                 CropImageCallback callback,
                                 Uri inputUri,
                                 Uri outputUri,
                                 long width,
                                 long height,
                                 int requestCode) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, outputUri));
            intent.setDataAndType(inputUri, "image/*");
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            intent.putExtra("aspectX", activity.dp2px(width));
            intent.putExtra("aspectY", activity.dp2px(height));
            intent.putExtra("crop", "true");
            intent.putExtra("scale", true);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("outputX", activity.dp2px(width));
            intent.putExtra("outputY", activity.dp2px(height));

            callback.onCrop(intent, requestCode);
        } catch (Exception e) {
            activity.showCenterToast(e.getMessage());
        }
    }

    public static Uri getCacheUri(Context context) {
        return FileProvider.getUriForFile(
                context,
                "com.core.provider",
                new File(Objects.requireNonNull(FileUtil.getCacheFile(
                        context,
                        FileUtil.getTemporaryJPG()
                )))
        );
    }

    public static Uri getCacheCropUri(Context context) {
        return FileProvider.getUriForFile(
                context,
                "com.core.provider",
                new File(Objects.requireNonNull(FileUtil.getCacheFile(
                        context,
                        FileUtil.getTemporaryJPG("crop")
                )))
        );
    }
}
