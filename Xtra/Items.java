package com.ulutsoft.nurlan.dsclient;

/**
 * Created by NURLAN on 02.07.2015.
 * Items (ID INTEGER PRIMARY KEY, Item STRING (150), Parent INTEGER, Code STRING (7), Price REAL)
 */
public class Items {
    private int ID;
    private String Item;
    private int Parent;
    private String Code;
    private float Price;

    public void setID(int ID){
        this.ID = ID;
    }

    public void setItem(String item){
        this.Item = item;
    }

    public void setParent(int parent){
        this.Parent = parent;
    }

    public void setCode(String code){
        this.Code = code;
    }

    public void setPrice(float price){
        this.Price = price;
    }

    public int getID(){
        return ID;
    }

    public String getItem(){
        return Item;
    }

    public int getParent(){
        return Parent;
    }

    public String getCode(){
        return Code;
    }

    public float getPrice(){
        return Price;
    }
}
