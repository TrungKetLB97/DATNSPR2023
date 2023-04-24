package com.app.projectfinal_master.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressBarDialog {
    private static ProgressBarDialog manager = null;

    private Context context;

    private ProgressDialog pDialog = null;

    private ProgressBarDialog(Context context) {
        this.context = context;

    }

    public static ProgressBarDialog getInstance(Context context) {
        if (manager == null)
            manager = new ProgressBarDialog(context);
        return manager;
    }

    public void ShowDialog(String msg, Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(msg);
        pDialog.show();
    }

    public void CloseDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }
}
