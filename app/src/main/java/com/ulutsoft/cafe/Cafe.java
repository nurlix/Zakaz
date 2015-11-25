package com.ulutsoft.cafe;

import android.app.Application;
import android.content.SharedPreferences;

import com.ulutsoft.cafe.Objects.Order;

import java.io.File;

/**
 * Created by NURLAN on 03.08.2015.
 */
public class Cafe extends Application {

    public Order order = new Order();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int userid = 0;
    String username;
    String userpass;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        File f = new File("/data/data/" + getPackageName() + "/shared_prefs/settings.xml");

        if(!f.exists()) {
            sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("server", "");
            editor.putString("port", "");
            editor.commit();
        }
    }

    //Getters
    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getUserpass() {
        return userpass;
    }

    public String getServer() {
        if(sharedPreferences.getString("server", "") != "") {
            return "http://" + sharedPreferences.getString("server", null) + ":" + sharedPreferences.getString("port", null) + "/data/";
        } else {
            return "";
        }
    }

    public Order getOrder() {
        return order;
    }

    //Setters
    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public void setServer(String server) {
        editor.putString("server", server);
        editor.commit();
    }

    public void setPort(String port) {
        editor.putString("port", port);
        editor.commit();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}

