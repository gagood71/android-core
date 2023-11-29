package com.core.pager;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.core.R;
import com.core.configuration.Configuration;
import com.core.pager.adapter.PageAdapter;
import com.core.pager.listeners.PageChangeListener;

@SuppressWarnings("rawtypes")
public class PagerLayout extends Pager {
    protected static int TAB_BACKGROUND = Configuration.Color.MAIN_COLOR_1;

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
    public void initView(View view) {
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tabLayout.getRootView().setBackgroundColor(
                    getContext().getColor(TAB_BACKGROUND));
        } else {
            tabLayout.getRootView().setBackgroundColor(
                    getContext().getResources().getColor(TAB_BACKGROUND));
        }

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initViewPager(PageAdapter adapter, PageChangeListener listener) {
        viewPager.addOnPageChangeListener(listener);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }
}
