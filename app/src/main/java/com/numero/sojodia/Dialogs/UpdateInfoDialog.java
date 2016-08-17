package com.numero.sojodia.Dialogs;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.numero.sojodia.R;

public class UpdateInfoDialog {

    private AlertDialog.Builder dialogBuilder;

    public UpdateInfoDialog(Context context) {
        dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.update_info_title));
        dialogBuilder.setMessage(context.getString(R.string.update_info_message));
        dialogBuilder.setPositiveButton("OK", null);
    }

    public void show() {
        dialogBuilder.show();
    }
}
