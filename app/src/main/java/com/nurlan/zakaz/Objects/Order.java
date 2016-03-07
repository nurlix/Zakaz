package com.nurlan.zakaz.Objects;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by NURLAN on 10.07.2015.
 */

public class Order implements Serializable {

    int ID = 0;
    String OrderID;
    String OrderDate;
    int CustomerId;
    float OrderDiscount;
    int Agent;
    float Total;
    boolean Saved;

    String OrderCustomer;

    Calendar cal;
    DateFormat df;
    String date;

    public Order() {
        cal = new GregorianCalendar();
        df = new SimpleDateFormat("ddMMyyHHmmss");
        date = df.format(cal.getTime());
        Saved = false;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
        OrderID = "1." + CustomerId + "." + date.toString();
    }

    public float getOrderDiscount() {
        return OrderDiscount;
    }

    public void setOrderDiscount(float orderDiscount) {
        OrderDiscount = orderDiscount;
    }

    public String getOrderCustomer() {
        return OrderCustomer;
    }

    public void setOrderCustomer(String orderCustomer) {
        OrderCustomer = orderCustomer;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public void setAgent(int agent) {
        Agent = agent;
    }

    public int getAgent() {
        return Agent;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
    }

    public boolean isSaved() {
        return Saved;
    }

    public void setSaved(boolean saved) {
        Saved = saved;
    }
}


