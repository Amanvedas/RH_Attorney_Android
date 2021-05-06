package com.rafayee.RHAttorney.Helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;


import com.rafayee.RHAttorney.R;

import java.util.Objects;

public class ProgressDialog {
    private Context context;
    private Dialog dialog;
    public static ProgressDialog customProgress = null;

    public static ProgressDialog getInstance() {
        if (customProgress == null) {
            customProgress = new ProgressDialog();
        }
        return customProgress;
    }
    @SuppressLint("ResourceAsColor")
    public void showProgress(@NonNull Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_spinner);
        dialog.setCancelable(false);
        if(!((Activity) context).isFinishing()) {
            //show dialog
            dialog.show();
        }
    }
    public void hideProgress() {
        //dialog.dismiss();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}