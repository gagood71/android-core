package com.core.views.layouts.relative;

import android.content.Context;
import android.view.View;

public abstract class DynamicLinearLayout extends DefaultRelativeLayout {
    public DynamicLinearLayout(Context context) {
        super(context);
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
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract int getViewId();

    protected abstract void initView();

    protected abstract void initData();
}
