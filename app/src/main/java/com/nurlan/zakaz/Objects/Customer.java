package com.nurlan.zakaz.Objects;

import java.io.Serializable;

/**
 * Created by NURLAN on 30.07.2015.
 */
public class Customer implements Serializable{

    int id;
    String name;
    String phone;
    String adress;

    public Customer() {}

    public Customer(int id, String name, String phone, String adress){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.adress = adress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
