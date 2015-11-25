package com.ulutsoft.cafe.Objects;

/**
 * Created by NURLAN on 10.07.2015.
 */
public class OrderItem {
    private int OrderItemID;
    private int OrderID;
    private int ItemId;
    private String Item;
    private float InitialQuantity = 0;
    private float Quantity = 1;
    private float Price;
    private int flag;

    public OrderItem(){
        flag = 0;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getOrderItemID() {
        return OrderItemID;
    }

    public void setOrderItemID(int orderItemID) {
        OrderItemID = orderItemID;
    }

    public float getTotal() {
        return Quantity * Price;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public String getItem() {
        return Item;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public void setItem(String item) {
        Item = item;
    }

    public float getQuantity() {
        return Quantity;
    }

    public void setQuantity(float quantity) {
        Quantity = quantity;
    }

    public float getInitialQuantity() {
        return InitialQuantity;
    }

    public void setInitialQuantity(float initialQuantity) {
        InitialQuantity = initialQuantity;
    }
}
