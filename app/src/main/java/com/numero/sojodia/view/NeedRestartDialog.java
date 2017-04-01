package com.numero.sojodia.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.numero.sojodia.R;

public class NeedRestartDialog {

    private AlertDialog.Builder dialogBuilder;
    private OnPositiveButtonClickListener onPositiveButtonClickListener;

    public static NeedRestartDialog init(Context context) {
        return new NeedRestartDialog(context);
    }

    public NeedRestartDialog(Context context) {
        dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage(context.getString(R.string.message_need_update));
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton(R.string.positive_button_need_update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (onPositiveButtonClickListener != null) {
                    onPositiveButtonClickListener.onClick();
                }
            }
        });
    }

    public NeedRestartDialog setOnPositiveButtonClickListener(OnPositiveButtonClickListener onPositiveButtonClickListener) {
        this.onPositiveButtonClickListener = onPositiveButtonClickListener;
        return this;
    }

    public void show() {
        dialogBuilder.show();
    }

    public interface OnPositiveButtonClickListener {
        void onClick();
    }
}
