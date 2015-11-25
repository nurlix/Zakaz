package com.ulutsoft.cafe.Objects;

/**
 * Created by NURLAN on 30.07.2015.
 */
public class User {

    private int ID;
    private String Username;
    private String Password;

    public User(int id, String username, String password){
        ID = id;
        Username = username;
        Password = password;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }
}
