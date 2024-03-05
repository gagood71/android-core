package com.core.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.core.R;
import com.core.views.layouts.linear.NormalLinearLayout;

public class BottomLine extends NormalLinearLayout {
    protected LinearLayout lineLayout;

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
    protected void initView(View view) {
        lineLayout = view.findViewById(R.id.line_layout);
    }

    @Override
    protected int getViewId() {
        return R.layout.layout_bottom_line;
    }

    @Override
    public void setBackgroundColor(int color) {
        lineLayout.setBackgroundColor(color);
    }
}
