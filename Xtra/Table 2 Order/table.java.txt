package com.ulutsoft.nurlan.cafe.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ulutsoft.nurlan.cafe.Cafe;
import com.ulutsoft.nurlan.cafe.Order;
import com.ulutsoft.nurlan.cafe.OrderViewActivity;
import com.ulutsoft.nurlan.cafe.R;

/**
 * Created by NURLAN on 04.07.2015.
 */
public class Table extends RelativeLayout{

    Cafe cafe;

    Activity activity;
    int tagg;
    int uid;
    int oid;
    int tableid;
    int guestCount;
    float total;

    Order order;
    
    TextView wname;
    TextView table_name;
    TextView time_text;
    TextView guests;

    TableLayout tview;

    TableRow timerow;
    TableRow guestrow;
    TableRow totalrow;
    TableRow tableHeader;


    public Table(Activity context) {
        super(context);
        this.activity = context;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.table, this);

        cafe = (Cafe)context.getApplicationContext();
        order = new Order();

        tview = (TableLayout)findViewById(R.id.tview);
        tview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int myappuid = cafe.getUserid();

                if(uid == myappuid || uid == 0){

                    Intent intent = new Intent(getContext(), OrderViewActivity.class);
                    if(uid != 0) {
                        ShowOrder();
                    } else {
                        NewOrder();
                    }
                    activity.startActivity(intent);

                } else {
                    Dialog dialog = new Dialog(getContext(), "Внимание", "Это не ваши клиенты!", 1);
                }
            }
        });

        timerow = (TableRow)findViewById(R.id.time_row);
        tableHeader = (TableRow)findViewById(R.id.tableHeader);
        guestrow = (TableRow)findViewById(R.id.guest_row);
        totalrow = (TableRow)findViewById(R.id.total_row);
        wname = (TextView) findViewById(R.id.wname);
        table_name = (TextView) findViewById(R.id.table_name);
        time_text = (TextView) findViewById(R.id.time_text);
        guests = (TextView) findViewById(R.id.guest_count);
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getTableid() {
        return tableid;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
        table_name.setText("Стол " + tableid);
    }

    public int getTagg() {
        return tagg;
    }

    public void setTagg(int tag) {
        this.tagg = tag;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setWname(String value){
        if(!value.isEmpty()) {
            wname.setText(value);
            tableHeader.setBackgroundColor(Color.rgb(170, 0, 0));
            timerow.setVisibility(View.VISIBLE);
            guestrow.setVisibility(View.VISIBLE);
            //totalrow.setVisibility(View.VISIBLE);
        }
    }

    public void setTime_text(String value){
        time_text.setText(value);
    }

    public void setGuests(int value){
        guestCount = value;
        guests.setText(" " + value);
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    void ShowOrder(){
        order.setOrderID(oid);
        order.setOrderTable(tableid);
        order.setWaiter(uid);
        order.setGuests(guestCount);
        cafe.setOrder(order);
    }

    void NewOrder(){
        order.setOrderTable(tableid);
        order.setWaiter(cafe.getUserid());
        order.setGuests(1);
        cafe.setOrder(order);
    }
}
