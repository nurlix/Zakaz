package com.ulutsoft.nurlan.dsclient;

/**
 * Created by NURLAN on 02.07.2015.
 */
public class Categories {
    private int ID;
    private String Category;
    private int Parent;

    public void setID(int ID){
        this.ID = ID;
    }

    public void setCategory(String category){
        this.Category = category;
    }

    public void setParent(int parent){
        this.Parent = parent;
    }

    public int getID(){
        return ID;
    }

    public String getCategory(){
        return Category;
    }

    public int getParent(){ return Parent;
    }
}
