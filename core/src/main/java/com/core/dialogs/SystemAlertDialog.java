package com.core.dialogs;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.core.R;
import com.core.activities.v1.CompatActivity;
import com.core.views.NormalText;

public class SystemAlertDialog extends SystemDialog {
    NormalText systemMessageText;

    public SystemAlertDialog(CompatActivity compatActivity,
                             View.OnClickListener onCancelListener,
                             View.OnClickListener onDismissListener) {
        super(compatActivity, onCancelListener, onDismissListener);
    }

    @Override
    protected void onAfterCreate() {
        super.onAfterCreate();

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        layoutParams.width = 300;
        layoutParams.height = 300;

        window.setAttributes(layoutParams);
        window.setGravity(Gravity.CENTER);

        systemMessageText = containerView.findViewById(R.id.system_message_text);
        systemMessageText.setText(activity.getString(R.string.系統訊息));
    }

    @Override
    protected int getContainerViewId() {
        return R.layout.dialog_system;
    }
}
