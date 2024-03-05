package com.core.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class NormalText extends AppCompatTextView {
    TextWatcher textWatcher;

    String keyword;

    public NormalText(@NonNull Context context) {
        super(context);

        init();
    }

    public NormalText(@NonNull Context context,
                      @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public NormalText(@NonNull Context context,
                      @Nullable AttributeSet attrs,
                      int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    protected void init() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence,
                                          int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence,
                                      int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                keyword = editable.toString();
            }
        };

        addTextChangedListener(textWatcher);
    }

    public void setTextChangedListener(TextWatcher watcher) {
        removeTextChangedListener(textWatcher);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence,
                                          int i, int i1, int i2) {
                watcher.beforeTextChanged(charSequence, i, i1, i2);
            }

            @Override
            public void onTextChanged(CharSequence charSequence,
                                      int i, int i1, int i2) {
                watcher.onTextChanged(charSequence, i, i1, i2);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                keyword = editable.toString();

                watcher.afterTextChanged(editable);
            }
        };

        addTextChangedListener(textWatcher);
    }

    public String getKeyword() {
        return keyword;
    }
}
