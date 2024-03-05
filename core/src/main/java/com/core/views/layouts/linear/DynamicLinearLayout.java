package com.core.views.layouts.linear;

import android.content.Context;
import android.view.View;

public abstract class DynamicLinearLayout extends DefaultLinearLayout {
    public DynamicLinearLayout(Context context) {
        super(context);
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
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract int getViewId();

    protected abstract void initView(View view);

    protected abstract void initData();
}
