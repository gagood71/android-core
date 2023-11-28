package com.core.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerHolder extends RecyclerView.ViewHolder {
    public RecyclerHolder(@NonNull View itemView) {
        super(itemView);
    }
}
