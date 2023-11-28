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

    protected LinearLayout titleContainer;
    protected LinearLayout contentContainer;

    protected NormalText dialogCancel;
    protected NormalText dialogOk;

    public SystemDialog(CompatActivity compatActivity,
                        View.OnClickListener onCancelListener1,
                        View.OnClickListener onDismissListener1) {
        super(compatActivity);

        onCancelListener = onCancelListener1;
        onDismissListener = onDismissListener1;
    }

    @Override
    protected void onAfterCreate() {
        bottomLine1 = mainView.findViewById(R.id.dialog_bottom_line_1);
        titleContainer = mainView.findViewById(R.id.dialog_title_container);
        contentContainer = mainView.findViewById(R.id.dialog_content_container);
        dialogCancel = mainView.findViewById(R.id.dialog_cancel);
        dialogOk = mainView.findViewById(R.id.dialog_ok);

        dialogCancel.setOnClickListener(view -> {
            onCancelListener.onClick(view);

            dismiss();
        });

        dialogOk.setOnClickListener(view -> {
            onDismissListener.onClick(view);

            dismiss();
        });

        if (getContainerViewId() > 0) {
            containerView = View.inflate(activity, getContainerViewId(), null);

            titleContainer.removeAllViews();
            titleContainer.setVisibility(View.GONE);

            bottomLine1.setVisibility(View.GONE);

            contentContainer.setVisibility(View.VISIBLE);
            contentContainer.addView(containerView, 0);
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.dialog_layout;
    }

    protected void hideCancelButton() {
        dialogCancel.setVisibility(View.GONE);
    }

    protected void hideOkButton() {
        dialogOk.setVisibility(View.GONE);
    }

    protected abstract int getContainerViewId();
}
