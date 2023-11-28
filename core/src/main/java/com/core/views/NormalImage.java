package com.core.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;

public class NormalImage extends AppCompatImageView {
    public NormalImage(@NonNull Context context) {
        super(context);
    }

    public NormalImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NormalImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundDrawable(@Nullable Drawable background) {
        setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(getContext())
                .load(background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(this);
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        setScaleType(ImageView.ScaleType.CENTER_CROP);

        new Thread(() -> {
            try {
                FutureTarget<?> futureTarget = Glide.with(getContext())
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .submit();

                Glide.with(getContext())
                        .load(futureTarget.get())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
