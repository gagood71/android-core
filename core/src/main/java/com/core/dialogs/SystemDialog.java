package com.core.dialogs;

import android.view.View;
import android.widget.LinearLayout;

import com.core.R;
import com.core.activities.v1.CompatActivity;
import com.core.dialogs.v1.CompatDialog;
import com.core.views.NormalText;

public abstract class SystemDialog extends CompatDialog {
    protected View.OnClickListener onCancelListener;
    protected View.OnClickListener onDismissListener;

    protected View mainView;
    protected View bottomLine1;
    protected View containerView;

    protected LinearLayout titleLayout;
    protected LinearLayout contentLayout;

    protected NormalText cancelText;
    protected NormalText okText;

    public SystemDialog(CompatActivity compatActivity,
                        View.OnClickListener onCancelListener1,
                        View.OnClickListener onDismissListener1) {
        super(compatActivity);

        onCancelListener = onCancelListener1;
        onDismissListener = onDismissListener1;
    }

    @Override
    protected void onAfterCreate() {
        bottomLine1 = mainView.findViewById(R.id.bottom_line_1);
        titleLayout = mainView.findViewById(R.id.title_layout);
        contentLayout = mainView.findViewById(R.id.content_layout);
        cancelText = mainView.findViewById(R.id.cancel_text);
        okText = mainView.findViewById(R.id.ok_text);

        cancelText.setOnClickListener(view -> {
            onCancelListener.onClick(view);

            dismiss();
        });

        okText.setOnClickListener(view -> {
            onDismissListener.onClick(view);

            dismiss();
        });

        if (getContainerViewId() > 0) {
            containerView = View.inflate(activity, getContainerViewId(), null);

            titleLayout.removeAllViews();
            titleLayout.setVisibility(View.GONE);

            bottomLine1.setVisibility(View.GONE);

            contentLayout.setVisibility(View.VISIBLE);
            contentLayout.addView(containerView, 0);
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.dialog_layout;
    }

    protected void hideCancelButton() {
        cancelText.setVisibility(View.GONE);
    }

    protected void hideOkButton() {
        okText.setVisibility(View.GONE);
    }

    protected abstract int getContainerViewId();
}
