package com.core.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerAdapter<A, B extends RecyclerHolder>
        extends RecyclerView.Adapter<B> {
    protected Context context;

    protected List<A> dataList;

    public RecyclerAdapter(Context context1) {
        context = context1;
        dataList = new ArrayList<>();
    }

    public void add(A object) {
        dataList.add(object);

        notifyDataSetChanged();
    }

    public void remove(A object) {
        dataList.remove(object);

        notifyDataSetChanged();
    }

    public void remove(int position) {
        dataList.remove(position);

        notifyDataSetChanged();
    }

    public void setList(List<A> list) {
        dataList = list;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
