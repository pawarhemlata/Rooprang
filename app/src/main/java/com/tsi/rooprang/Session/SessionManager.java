package com.tsi.rooprang.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static SessionManager _instance = null;
    private static SharedPreferences pref = null;
    SharedPreferences.Editor editor = null;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Rooprang";
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERID = "userId";
    public static final String CHECK_LOGIN = "logincheck";
    public static final String KEY_PHONE = "PHONE";
    public static final String DEVICE_TOKEN = "DEVICE_TOKEN";
    public static final String KEY_LANGUAGE = "KEY_LANGUAGE";
    public static final String KEY_LANG_COUNTRY = "KEY_LANG_COUNTRY";

    public SessionManager(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }
    public void setDeviceToken(String vDeviceToken) {
        this.editor.putString(DEVICE_TOKEN, vDeviceToken);
        editor.commit();
    }

    public String getDeviceToken() {
        String deviceToken = pref.getString(DEVICE_TOKEN, "");
        return deviceToken;
    }
//    public SessionManager(Context context) {
//        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//    }

    public static SessionManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new SessionManager(context);
        }
        return _instance;
    }

    public String get_data_from_session(String pref_name) {
        return pref.getString(pref_name, "");
    }

    public void set_data_in_session(String pref_name, String pref_val) {
        editor = pref.edit();
        editor.putString(pref_name, pref_val);
        editor.commit();
    }


    public void clearPrefs() {
        editor = pref.edit();
        editor.clear();
        editor.commit();
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}