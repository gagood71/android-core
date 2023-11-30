package com.core.pager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.core.pager.adapter.PageAdapter;
import com.core.pager.listeners.PageChangeListener;

@SuppressWarnings("rawtypes")
public class PagerLayout extends Pager {
    public PagerLayout(Context context) {
        super(context);
    }

    public PagerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PagerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void initViewPager(PageAdapter adapter, PageChangeListener listener) {
        viewPager.addOnPageChangeListener(listener);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }
}
