package com.ulutsoft.nurlan.cafe.android;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NURLAN on 30.07.2015.
 */
public class Session {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    final static String PREFS = "settings";

    final static String USER  = "user";
    final static String USERID  = "userid";
    final static String USERPASS  = "userpass";
    final static String SERVER = "server";
    final static String PORT = "port";

    public Session(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //Setters
    public void setUser(String user) {
        editor.putString(USER, user);
        editor.commit();
    }
    public void setUserid(Integer userid) {
        editor.putInt(USERID, userid);
        editor.commit();
    }
    public void setPassword(String userpass){
        editor.putString(USERPASS, userpass);
        editor.commit();
    }
    public void setServer(String server) {
        editor.putString(SERVER,server);
        editor.commit();
    }
    public void setPort(String port) {
        editor.putString(PORT,port);
        editor.commit();
    }

    //Getters
    public String getUser() { return sharedPreferences.getString(USER, null); }
    public Integer getUserid() { return sharedPreferences.getInt(USERID, 0); }
    public String getPassword() { return sharedPreferences.getString(USERPASS, null); }
    public String getServer() {
        if(sharedPreferences.getString(SERVER, "") != "") {
            return "http://" + sharedPreferences.getString(SERVER, null) + ":" + sharedPreferences.getString(PORT, null) + "/data/";
        } else {
            return "";
        }
    }
    public String getPort() { return sharedPreferences.getString(PORT, null); }

    //Helpers
    public Boolean active(){
        if(sharedPreferences.getInt(USERID,0) == 0)
            return false;
        else
            return true;
    }

    public void close(){
        editor.putString(USER,"");
        editor.putInt(USERID, 0);
        editor.putString(USERPASS, "");
        editor.commit();
    }
}
