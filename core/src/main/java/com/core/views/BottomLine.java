package com.core.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.core.R;
import com.core.views.layouts.linear.NormalLinearLayout;

public class BottomLine extends NormalLinearLayout {
    protected LinearLayout viewLine;

    public BottomLine(Context context) {
        super(context);
    }

    public BottomLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BottomLine(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initView() {
        mainView.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        viewLine = mainView.findViewById(R.id.view_line);
    }

    @Override
    protected int getViewId() {
        return R.layout.layout_bottom_line;
    }

    @Override
    public void setBackgroundColor(int color) {
        viewLine.setBackgroundColor(color);
    }
}
