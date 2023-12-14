package com.core.views.layouts.relative;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.core.views.layouts.CompatLayout;

public abstract class DefaultRelativeLayout extends RelativeLayout
        implements CompatLayout {
    protected View mainView;

    public DefaultRelativeLayout(Context context) {
        super(context);

        init();
    }

    public DefaultRelativeLayout(Context context,
                                 @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
        initAttributeSet(context, attrs);
    }

    public DefaultRelativeLayout(Context context,
                                 @Nullable AttributeSet attrs,
                                 int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
        initAttributeSet(context, attrs);
    }

    public DefaultRelativeLayout(Context context,
                                 AttributeSet attrs,
                                 int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
        initAttributeSet(context, attrs);
    }

    @Override
    public void setMarginTop(int value) {
        LayoutParams params = (LayoutParams) mainView.getLayoutParams();
        params.topMargin = value;

        mainView.setLayoutParams(params);
    }

    @Override
    public void setMarginBottom(int value) {
        LayoutParams params = (LayoutParams) mainView.getLayoutParams();
        params.bottomMargin = value;

        mainView.setLayoutParams(params);
    }

    @Override
    public void setMarginLeft(int value) {
        LayoutParams params = (LayoutParams) mainView.getLayoutParams();
        params.leftMargin = value;

        mainView.setLayoutParams(params);
    }

    @Override
    public void setMarginRight(int value) {
        LayoutParams params = (LayoutParams) mainView.getLayoutParams();
        params.rightMargin = value;

        mainView.setLayoutParams(params);
    }

    @Override
    public void setPaddingTop(int value) {
        int bottom = mainView.getPaddingBottom();
        int left = mainView.getPaddingLeft();
        int right = mainView.getPaddingRight();

        mainView.setPadding(left, value, right, bottom);
    }

    @Override
    public void setPaddingBottom(int value) {
        int top = mainView.getPaddingTop();
        int left = mainView.getPaddingLeft();
        int right = mainView.getPaddingRight();

        mainView.setPadding(left, top, right, value);
    }

    @Override
    public void setPaddingLeft(int value) {
        int top = mainView.getPaddingTop();
        int bottom = mainView.getPaddingBottom();
        int right = mainView.getPaddingRight();

        mainView.setPadding(value, top, right, bottom);
    }

    @Override
    public void setPaddingRight(int value) {
        int top = mainView.getPaddingTop();
        int bottom = mainView.getPaddingBottom();
        int left = mainView.getPaddingLeft();

        mainView.setPadding(left, top, value, bottom);
    }

    private Toast makeText(String text) {
        return Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
    }

    private Toast makeText(int resourceId) {
        return Toast.makeText(getContext(), resourceId, Toast.LENGTH_SHORT);
    }

    protected void initAttributeSet(Context context, AttributeSet attributeSet) {
    }

    protected void showCenterToast(String text) {
        Toast toast = makeText(text);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    protected void showCenterToast(int resourceId) {
        Toast toast = makeText(resourceId);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    protected abstract void init();
}
