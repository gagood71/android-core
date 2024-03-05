package com.core.views.layouts.linear;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public abstract class NormalLinearLayout extends DefaultLinearLayout {
    public NormalLinearLayout(Context context) {
        super(context);
    }

    public NormalLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NormalLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NormalLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        try {
            View view = View.inflate(getContext(), getViewId(), null);
            view.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            removeAllViews();
            addView(view);
            initView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void initView(View view);

    protected abstract int getViewId();
}
