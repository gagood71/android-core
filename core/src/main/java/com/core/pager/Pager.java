package com.core.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.core.R;
import com.core.pager.adapter.PageAdapter;
import com.core.pager.listeners.PageChangeListener;
import com.core.views.layouts.linear.NormalLinearLayout;
import com.google.android.material.tabs.TabLayout;

@SuppressWarnings("rawtypes")
public abstract class Pager extends NormalLinearLayout {
    protected TabLayout tabLayout;

    protected ViewPager viewPager;

    public Pager(Context context) {
        super(context);
    }

    public Pager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Pager(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Pager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void initView(View view) {
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getViewId() {
        return R.layout.layout_pager;
    }

    public abstract void initViewPager(PageAdapter adapter, PageChangeListener listener);

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
