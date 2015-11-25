package com.ulutsoft.cafe.Objects;

import java.util.List;

/**
 * Created by NURLAN on 10.07.2015.
 */
public class Order{

    private final int FGINSERT = 1;
    private final int FGUPDATE = 2;

    private int OrderID;
    private String OrderDate;
    private int OrderTable;
    private int Waiter;
    private int Guests;
    private byte OrderStatus;
    private int OrderRoom;
    String Roomname;


    private List<OrderItem> orderItems;

    //New Order ctr
    public Order(){
        OrderID = 0;
        Guests = 1;
    }

    //Load Order ctr
    public Order(int OrderID, String OrderDate, int OrderTable, int Waiter, int Guests, String room, byte OrderStatus) {
        this.OrderID = OrderID;
        this.OrderDate = OrderDate;
        this.OrderTable = OrderTable;
        this.Waiter = Waiter;
        this.Guests = Guests;
        this.Roomname = room;
        this.OrderStatus = OrderStatus;
    }

    public int getGuests() {
        return Guests;
    }

    public void setGuests(int guests) {
        Guests = guests;
    }

    public byte getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(byte orderStatus) {
        OrderStatus = orderStatus;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public int getOrderTable() {
        return OrderTable;
    }

    public void setOrderTable(int orderTable) {
        OrderTable = orderTable;
    }

    public int getWaiter() {
        return Waiter;
    }

    public void setWaiter(int waiter) {
        Waiter = waiter;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public int getOrderRoom() {
        return OrderRoom;
    }

    public void setOrderRoom(int orderRoom) {
        OrderRoom = orderRoom;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getRoomname() { return Roomname; }

    public void setRoomname(String roomname) {
        Roomname = roomname;
    }

    public String SaveOrder(){
        String result;
        if(OrderID == 0) {
            result = "insert into orders(orderdate, guests, waiter, ordertable, OrderRoom) VALUES(NOW(),"
                    + Guests + ", "
                    + Waiter + ", "
                    + OrderTable + ", "
                    + OrderRoom
                    + "); \nset @ID = LAST_INSERT_ID(); \n";
        } else {
            result = "update orders set ordertable = " + OrderTable + ", guests = " + Guests + ", OrderRoom = " + OrderRoom + " where ID = " + OrderID + "; \n";
        }
        result = result + fillList();
        result = result +  "select @ID as Result \n";
        return result;
    }

    private String fillList(){
        String list = "";
        for(OrderItem oi : orderItems){
            if(oi.getFlag() == FGINSERT){
                if(OrderID == 0)
                    list = list + "INSERT INTO orderdetails (OrderID, Item, Quantity, Price, ItemPrinted)  VALUES (@ID, " + oi.getItemId() + ", " + oi.getQuantity() + ", " + oi.getPrice() + ", 0); \n";
                else
                    list = list + "INSERT INTO orderdetails (OrderID, Item, Quantity, Price, ItemPrinted)  VALUES (" + OrderID + ", " + oi.getItemId() + ", " + oi.getQuantity() + ", " + oi.getPrice() + ", 0); \n";
            }
        }
        return list;
    }

    public String CloseOrder() {
        return "update orders set status = 2, PayDate = NOW() where ID = " + OrderID + ";\nselect 'OK' as Result\n";
    }
}


