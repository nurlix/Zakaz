package com.ulutsoft.nurlan.cafe.android;

/**
 * Created by NURLAN on 29.08.2015.
 */
public class Items {
    int ID;
    String Item;
    int Category;
    int Parent;
    float Price;

    //Setters
    public void setID(int ID){
        this.ID = ID;
    }

    public void setItem(String item){
        this.Item = item;
    }

    public void setCategory(int category) {
        Category = category;
    }

    public void setParent(int parent){
        this.Parent = parent;
    }

    public void setPrice(float price){
        this.Price = price;
    }

    //Getters
    public int getID(){
        return ID;
    }

    public String getItem(){
        return Item;
    }

    public int getCategory() {
        return Category;
    }

    public int getParent(){
        return Parent;
    }

    public float getPrice(){
        return Price;
    }
}
