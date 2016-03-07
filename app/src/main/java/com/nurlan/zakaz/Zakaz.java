package com.nurlan.zakaz;

import android.app.Application;
import android.content.SharedPreferences;

import java.io.File;

/**
 * Created by NURLAN on 03.08.2015.
 */

public class Zakaz extends Application {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        File f = new File("/data/data/" + getPackageName() + "/shared_prefs/settings.xml");

        if(!f.exists())
        {
            editor.putString("server", "");
            editor.putInt("agent", 0);
            editor.commit();
        }
    }

    public String getServer() {
        if(!sharedPreferences.getString("server", "").isEmpty()) { // != ""
            return sharedPreferences.getString("server", null);
        } else {
            return "";
        }
    }

    public void setServer(String server) {
        editor.putString("server", server);
        editor.commit();
    }

    public void setAgent(int agent) {
        editor.putInt("agent", agent);
        editor.commit();
    }

    public int getAgent() {
        return sharedPreferences.getInt("agent", 0);
    }
}
