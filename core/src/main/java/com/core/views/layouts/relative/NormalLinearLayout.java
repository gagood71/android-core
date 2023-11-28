package com.core.views.layouts.relative;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public abstract class NormalLinearLayout extends DefaultRelativeLayout {
    public NormalLinearLayout(Context context) {
        super(context);
    }

    public NormalLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NormalLinearLayout(Context context,
                              @Nullable AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NormalLinearLayout(Context context,
                              AttributeSet attrs,
                              int defStyleAttr,
                              int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        try {
            mainView = View.inflate(getContext(), getViewId(), null);
            mainView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            removeAllViews();
            addView(mainView);

            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void initView();

    protected abstract int getViewId();
}
