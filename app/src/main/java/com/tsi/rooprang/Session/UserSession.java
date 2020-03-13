package com.tsi.rooprang.Session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;


public class UserSession {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "UserSessionPref";

    public static final String FIRST_TIME = "firsttime";
    public static final String KEY_USERID = "userId";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";

    public static final String KEY_EMAIL = "email";


    public static final String KEY_MOBiLE = "mobile";


    public static final String KEY_PHOTO = "photo";


    public static final String KEY_CART = "cartvalue";


    public static final String KEY_WISHLIST = "wishlistvalue";


    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    Intent intent;

    // Constructor
    public UserSession(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String email, String mobile, String password) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBiLE, mobile);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    public HashMap<String, String> getUserLogin() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        return user;
    }


    public void createSession(String name, String password) {
//        editor.putBoolean(IS_LOGIN, isLogin);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

   /* public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            intent = new Intent(context, DrawerActivity.class);
        } else {
            intent = new Intent(context, LoginActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }*/


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user phone number
        user.put(KEY_MOBiLE, pref.getString(KEY_MOBiLE, null));

        // user avatar
        user.put(KEY_PHOTO, pref.getString(KEY_PHOTO, null));

        // return user
        return user;
    }


   /* public void logoutUser() {
        editor.putBoolean(IS_LOGIN, false);
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }*/


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public int getCartValue() {
        return pref.getInt(KEY_CART, 0);
    }

    public int getWishlistValue() {
        return pref.getInt(KEY_WISHLIST, 0);
    }

    public Boolean getFirstTime() {
        return pref.getBoolean(FIRST_TIME, true);
    }

    public void setFirstTime(Boolean n) {
        editor.putBoolean(FIRST_TIME, n);
        editor.commit();
    }






    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}