package com.rafayee.RHAttorney.Helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    public boolean checkNetwork(Context context) {
        boolean wifiAvailable = false;
        boolean mobileAvailable = false;
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
        for (NetworkInfo netInfo : networkInfo) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (netInfo.isConnected())
                    wifiAvailable = true;
            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (netInfo.isConnected())
                    mobileAvailable = true;
        }
        return wifiAvailable || mobileAvailable;
    }

}