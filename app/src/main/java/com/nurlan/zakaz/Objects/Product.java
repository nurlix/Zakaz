package com.nurlan.zakaz.Objects;

/**
 * Created by NURLAN on 07.07.2015.
 */

public class Product {
    int id;
    int  parent;
    String item;
    float price;
    int isFolder;

    public Product() {}

    public Product(int id, int parent, String item, float price, int isFolder){
        this.id = id;
        this.parent = parent;
        this.item = item;
        this.price = price;
        this.isFolder = isFolder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() { return parent; }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setIsFolder(int isFolder) {
        this.isFolder = isFolder;
    }

    public int getIsFolder() {
        return isFolder;
    }

    public boolean IsFolder() {
        return isFolder == 0 ? false : true;
    }
}
