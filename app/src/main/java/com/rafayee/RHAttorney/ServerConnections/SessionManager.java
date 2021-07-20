package com.rafayee.RHAttorney.ServerConnections;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.rafayee.RHAttorney.Helpers.ProgressDialog;
import com.rafayee.RHAttorney.Login.View.LoginActivity;
import java.io.File;
import java.util.Objects;

public class SessionManager {
    // Shared Preferences
    final SharedPreferences pref;
    // Editor for Shared preferences
    final SharedPreferences.Editor editor;
    // Context
    final Context _context;
    // Shared pref mode
    final int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "RH_Attorney";
    //vmartApp
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USERID = "email";
    //public static final String IS_ONLINE = "false";
    String email,password,type;
    boolean isRem;
    boolean finger = false;
    boolean face = false;

    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String userid) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        ///storing the mobile value
        editor.putString(KEY_USERID, userid);
        // commit changes
        editor.commit();
    }

    public void logoutUser() {
        editor.putBoolean(IS_LOGIN, false);
        editor.clear();
        editor.apply();
        SharedPreferences myPrefs = _context.getSharedPreferences("LoginPref",
                Context.MODE_PRIVATE);
        email = myPrefs.getString("emailID","");
        password = myPrefs.getString("password","");
        isRem = myPrefs.getBoolean("isRemember",false);
        finger = myPrefs.getBoolean("enableTouchID",false);
        face = myPrefs.getBoolean("enableFaceID",false);
        type = myPrefs.getString("register_type","");

        Log.e("logout", " ::"+isRem+" :: "+email+" :: "+password);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.clear();
        editor.apply();
        @SuppressLint("SdCardPath") File sharedPreferenceFile = new File("/data/data/"+ _context.getPackageName()+ "/shared_prefs/");
        File[] listFiles = sharedPreferenceFile.listFiles();
        for (File file : Objects.requireNonNull(listFiles)) {
            file.delete();
        }
        Log.e("logoutt", " ::"+isRem+" :: "+email+" :: "+password);
        if (type.equals("Manual")) {
            if (isRem) {
                editor.putString("password", password);
                editor.putString("emailID", email);
                editor.putBoolean("isRemember", isRem);
                /*editor.putBoolean("enableTouchID", finger);
                editor.putBoolean("enableFaceID", face);*/
                editor.apply();
                //editor.apply();
            }/*else {
                editor.putBoolean("enableTouchID", finger);
                editor.putBoolean("enableFaceID", face);
                editor.apply();
            }*/
        }/*else {
            editor.putBoolean("enableTouchID", finger);
            editor.putBoolean("enableFaceID", face);
            editor.apply();
        }*/

        Intent i = new Intent(_context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        _context.startActivity(i);
        ProgressDialog.getInstance().hideProgress();
    }

    public void logoutUser_Main() {
        editor.putBoolean(IS_LOGIN, false);
        editor.clear();
        editor.apply();
        SharedPreferences myPrefs = _context.getSharedPreferences("LoginPref",
                Context.MODE_PRIVATE);
        email = myPrefs.getString("emailID","");
        password = myPrefs.getString("password","");
        isRem = myPrefs.getBoolean("isRemember",false);
        finger = myPrefs.getBoolean("enableTouchID",false);
        face = myPrefs.getBoolean("enableFaceID",false);
        type = myPrefs.getString("register_type","");

        Log.e("logout", " ::"+isRem+" :: "+email+" :: "+password);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.clear();
        editor.apply();
        @SuppressLint("SdCardPath") File sharedPreferenceFile = new File("/data/data/"+ _context.getPackageName()+ "/shared_prefs/");
        File[] listFiles = sharedPreferenceFile.listFiles();
        for (File file : Objects.requireNonNull(listFiles)) {
            file.delete();
        }
        Log.e("logoutt", " ::"+isRem+" :: "+email+" :: "+password);
        if (type.equals("Manual")) {
            if (isRem) {
                editor.putString("password", password);
                editor.putString("emailID", email);
                editor.putBoolean("isRemember", isRem);
                /*editor.putBoolean("enableTouchID", finger);
                editor.putBoolean("enableFaceID", face);*/
                editor.apply();
                //editor.apply();
            }/*else {
                editor.putBoolean("enableTouchID", finger);
                editor.putBoolean("enableFaceID", face);
                editor.apply();
            }*/
        }/*else {
            editor.putBoolean("enableTouchID", finger);
            editor.putBoolean("enableFaceID", face);
            editor.apply();
        }*/

        /*Intent i = new Intent(_context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        _context.startActivity(i);
        ProgressDialog.getInstance().hideProgress();*/
    }

    /**
     * Quick checkky for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        String userid = pref.getString(SessionManager.KEY_USERID, "");
        Log.e("sessionMobile", " " + userid);
        return pref.getBoolean(IS_LOGIN, false);
    }
}