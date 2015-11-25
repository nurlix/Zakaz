package com.ulutsoft.cafe.Objects;

/**
 * Created by NURLAN on 21.11.2015.
 */
public class Table {

    int ID;
    String TableName;
    int RoomID;

    public Table(int id, String tableName, int roomID) {

        ID = id;
        TableName = tableName;
        RoomID = roomID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int roomID) {
        RoomID = roomID;
    }
}
