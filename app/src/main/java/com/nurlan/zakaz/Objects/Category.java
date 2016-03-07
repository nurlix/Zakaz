package com.nurlan.zakaz.Objects;

import java.util.List;

/**
 * Created by NURLAN on 18.12.2015.
 */

public class Category {

    private int ID;
    private String Name;
    private List<Product> products;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public List<Product> getItems() {
        return products;
    }

    public void setItems(List<Product> products) {
        this.products = products;
    }
}
