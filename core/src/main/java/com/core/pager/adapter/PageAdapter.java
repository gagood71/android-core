package com.core.pager.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.core.fragment.DefaultFragment;

import java.util.List;

@SuppressWarnings({"deprecation"})
public class PageAdapter<T extends DefaultFragment> extends FragmentStatePagerAdapter {
    protected List<T> fragments;

    protected FragmentManager fragmentManager;
    protected FragmentTransaction fragmentTransaction;

    public PageAdapter(List<T> list, FragmentManager manager, FragmentTransaction transaction) {
        super(manager);

        fragments = list;
        fragmentManager = manager;
        fragmentTransaction = transaction;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }
}
