package com.core.views.layouts.linear;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.R;
import com.core.adapters.RecyclerAdapter;
import com.core.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public abstract class RefreshLayout<A, B extends RecyclerAdapter> extends NormalLinearLayout {
    protected LinearLayout loadingLayout;

    protected SwipeRefreshLayout swipeRefreshLayout;

    protected RecyclerView recyclerView;

    protected B adapter;

    protected List<A> dataList;

    private boolean isLoading;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        dataList = new ArrayList<>();
        isLoading = false;

        try {
            View view = View.inflate(getContext(), getViewId(), null);
            view.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            removeAllViews();
            addView(view);
            initView(view);
            initData();
            initAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView(View view) {
        loadingLayout = findViewById(R.id.loading_layout);
        loadingLayout.setVisibility(GONE);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Log.d("刷新事件", "下拉刷新");

            loadingLayout.setVisibility(VISIBLE);
            swipeRefreshLayout.setEnabled(false);
            recyclerView.setEnabled(false);
            recyclerView.setNestedScrollingEnabled(false);

            onPullRefresh();

            new Handler(Looper.getMainLooper()).postDelayed(
                    () -> {
                        loadingLayout.setVisibility(GONE);
                        swipeRefreshLayout.setEnabled(true);
                        recyclerView.setEnabled(true);
                        recyclerView.setNestedScrollingEnabled(true);
                    },
                    Configuration.Time.TIME_1000
            );
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1)) {
                    // 在這裡執行上拉刷新的操作，例如加載更多數據
                    if (!isLoading) {
                        Log.d("刷新事件", "上拉刷新");

                        isLoading = true;

                        onPullUpRefresh();

                        new Handler(Looper.getMainLooper()).postDelayed(
                                () -> isLoading = false,
                                Configuration.Time.TIME_1000
                        );
                    }
                }
            }
        });
    }

    @Override
    protected int getViewId() {
        return R.layout.layout_refresh;
    }

    protected abstract void initData();

    protected abstract void initAdapter();

    protected abstract void onPullRefresh();

    protected abstract void onPullUpRefresh();
}
