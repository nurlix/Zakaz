package com.ulutsoft.nurlan.dsclient;

/**
 * Created by NURLAN on 11.07.2015.
 */
public class Table {

    private int TableId;
    private String TableName;
    private String Waiter;
    private String Time;
    private int Guests;

    public Table(int tableId, String tableName, String waiter, String time, int guests){
        TableId = tableId;
        TableName  = tableName;
        Waiter = waiter;
        Time = time;
        Guests = guests;
    }

    public int getTableId() {
        return TableId;
    }

    public void setTableId(int tableId) {
        TableId = tableId;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getWaiter() {
        return Waiter;
    }

    public void setWaiter(String waiter) {
        Waiter = waiter;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getGuests() {
        return Guests;
    }

    public void setGuests(int guests) {
        Guests = guests;
    }
}
