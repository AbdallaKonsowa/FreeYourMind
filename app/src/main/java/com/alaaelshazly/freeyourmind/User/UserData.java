package com.alaaelshazly.freeyourmind.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

public class UserData {

    public static final String KEY_NAME = "user_name";
    public static final String KEY_PASS = "user_pass";
    public static final String KEY_STATUS = "user_status";
    public static final String Book_NAME = "book_name";
    public static final ArrayList<String> KEY_BOOKS = null;


    private static final String File_Name = "UserFile";
    SharedPreferences mSP;
    SharedPreferences.Editor mSEDit;
    Context mContext;


    @SuppressLint("CommitPrefEdits")
    public UserData(Context mContext) {
        this.mContext = mContext;
        mSP = mContext.getSharedPreferences(File_Name, Context.MODE_PRIVATE);
        mSEDit = mSP.edit();
    }

    public void saveData(String name, String mail, Boolean state) {
        mSEDit.putString(KEY_NAME, name);
        mSEDit.putString(KEY_PASS, mail);
        mSEDit.putBoolean(KEY_STATUS, state);
        mSEDit.apply();
    }

    public HashMap<String, String> getUserData() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_NAME, mSP.getString(KEY_NAME, null));
        user.put(KEY_PASS, mSP.getString(KEY_PASS, null));
        return user;
    }

    public Boolean isLogin() {
        return mSP.getBoolean(KEY_STATUS, false);
    }

    public String hasMail() {
        return mSP.getString(KEY_NAME, null);
    }

}
