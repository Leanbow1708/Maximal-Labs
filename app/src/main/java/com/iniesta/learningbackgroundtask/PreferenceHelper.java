package com.iniesta.learningbackgroundtask;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class PreferenceHelper {




    private final String location = "location";
    private final String hashtag = "hashtag";
    private final String data = "data";
    private SharedPreferences app_prefs;

    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.context = context;
    }


    public void putLocation(String loc) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(location, loc);
        edit.commit();
    }

    public String getLocation() {
        return app_prefs.getString(location, "");
    }





    public void putHashtag(String hash) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(hashtag, hash);
        edit.commit();
    }

    public String getHashtag() {
        return app_prefs.getString(hashtag, "");
    }




    public void putData(String  dat) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(data, dat);
        edit.commit();
    }

    public String getData() {
        return app_prefs.getString(data, "");
    }






}



