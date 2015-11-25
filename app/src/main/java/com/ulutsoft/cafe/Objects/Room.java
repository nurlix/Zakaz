package com.ulutsoft.cafe.Objects;

/**
 * Created by NURLAN on 21.11.2015.
 */
public class Room {

    int RoomId;
    String RoomName;
    int RoomPrinter;

    public Room(int roomId, String roomName, int roomPrinter) {
        RoomId = roomId;
        RoomName = roomName;
        RoomPrinter = roomPrinter;
    }

    public int getRoomId() {
        return RoomId;
    }

    public void setRoomId(int roomId) {
        RoomId = roomId;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public int getRoomPrinter() {
        return RoomPrinter;
    }

    public void setRoomPrinter(int roomPrinter) {
        RoomPrinter = roomPrinter;
    }
}
