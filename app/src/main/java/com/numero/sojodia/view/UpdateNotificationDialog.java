package com.numero.sojodia.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.numero.sojodia.R;

public abstract class UpdateNotificationDialog {
    private AlertDialog.Builder dialogBuilder;

    public UpdateNotificationDialog(Context context) {
        dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage(context.getString(R.string.message_update));
        dialogBuilder.setPositiveButton(R.string.positive_button_update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onClickPositiveButton();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);

    }

    public void show() {
        dialogBuilder.show();
    }

    public abstract void onClickPositiveButton();
}
