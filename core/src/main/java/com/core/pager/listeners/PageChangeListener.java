package com.core.pager.listeners;

import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.core.fragment.DefaultFragment;

import java.util.List;

@SuppressWarnings("rawtypes")
public abstract class PageChangeListener<T extends DefaultFragment> implements ViewPager.OnPageChangeListener {
    protected List<T> fragments;

    protected FragmentManager fragmentManager;
    protected FragmentTransaction fragmentTransaction;

    public PageChangeListener(List<T> fragmentList, FragmentManager manager, FragmentTransaction transaction) {
        fragments = fragmentList;
        fragmentManager = manager;
        fragmentTransaction = transaction;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        T fragment = fragments.get(position);

        Log.e(getClass().getName(), fragment.getClass().getName());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
