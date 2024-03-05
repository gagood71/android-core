package com.core.views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.core.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NormalButton extends AppCompatButton implements TextWatcher {
    protected static final long DELAY_100 = 100;

    protected static int UNABLE_BACKGROUND = R.drawable.normal_button_unable;
    protected static int UNABLE_TEXT_COLOR = R.color.white;

    protected static int ENABLE_BACKGROUND = R.drawable.normal_button_enable;
    protected static int ENABLE_TEXT_COLOR = R.color.white;

    protected ArrayList<EditText> editTexts;

    protected Timer timer;
    protected TimerTask timerTask;

    protected boolean isValid = false;

    public NormalButton(@NonNull Context context) {
        super(context);

        init();
    }

    public NormalButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public NormalButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @Override
    public void setOnClickListener(@NonNull OnClickListener l) {
        OnClickListener listener = view -> {
            enable();

            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    unable();

                    timerTask.cancel();
                    timer.cancel();

                    new Handler(Looper.getMainLooper()).post(() -> l.onClick(view));
                }
            };
            timer.schedule(timerTask, DELAY_100);
        };

        super.setOnClickListener(listener);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        isValid = true;

        for (EditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                isValid = false;
                break;
            }
        }

        setEnabled(isValid);
    }

    protected void init() {
        editTexts = new ArrayList<>();

        setBackgroundResource(UNABLE_BACKGROUND);
        setTextColor(getContext().getResources().getColor(UNABLE_TEXT_COLOR));
        addTextChangedListener(this);
    }

    protected void enable() {
        new Thread(() -> {
            try {
                setBackgroundResource(ENABLE_BACKGROUND);
                setTextColor(getContext().getResources().getColor(ENABLE_TEXT_COLOR));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected void unable() {
        new Thread(() -> {
            try {
                setBackgroundResource(UNABLE_BACKGROUND);
                setTextColor(getContext().getResources().getColor(UNABLE_TEXT_COLOR));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void addWatch(EditText editText) {
        editTexts.add(editText);
    }

    public static void setUnableBackground(int unableBackground) {
        UNABLE_BACKGROUND = unableBackground;
    }

    public static void setUnableTextColor(int unableTextColor) {
        UNABLE_TEXT_COLOR = unableTextColor;
    }

    public static void setEnableBackground(int enableBackground) {
        ENABLE_BACKGROUND = enableBackground;
    }

    public static void setEnableTextColor(int enableTextColor) {
        ENABLE_TEXT_COLOR = enableTextColor;
    }
}
