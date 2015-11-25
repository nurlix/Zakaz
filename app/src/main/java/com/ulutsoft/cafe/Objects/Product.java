package com.ulutsoft.cafe.Objects;

/**
 * Created by NURLAN on 07.07.2015.
 */
public class Product {
    private int id;
    private int parent;
    private int category;
    private String item;
    private float price;

    public Product() {}

    public Product(int id, int parent, int category, String item, float price){
        this.id = id;
        this.parent = parent;
        this.category = category;
        this.item = item;
        this.price = price;
    }

    public boolean isCategory() {
        if(category == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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
}
