package com.ulutsoft.nurlan.cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ulutsoft.nurlan.cafe.android.FlowLayout;
import com.ulutsoft.nurlan.cafe.android.HTTPRequest;
import com.ulutsoft.nurlan.cafe.android.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderActivity extends Activity {

    FlowLayout fl;
    FlowLayout.LayoutParams flowLP;
    private String url;
    LoadTables loadTables;

    Cafe cafe;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        //fl = (FlowLayout)findViewById(R.id.fl);
        //flowLP = new FlowLayout.LayoutParams(5, 5);

        cafe = (Cafe)getApplicationContext();
        url = cafe.getServer();
        //order = cafe.getOrder();

        //loadTables = new LoadTables();
        //loadTables.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if(data.getIntExtra("result", 0) != 0){
                order = new Order();
                order.setOrderTable(data.getIntExtra("result", 0));
                order.setWaiter(cafe.getUserid());
                order.setGuests(1);
                cafe.setOrder(order);
                Intent intent = new Intent(this, OrderViewActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent np = new Intent(this, NumpadActivity.class);
        startActivityForResult(np, 1);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, UsersActivity.class);
        startActivity(intent);
    }

    class LoadTables extends AsyncTask<Void, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(Void... params) {
            String query = "select " +
                    "odr.id, " +
                    "DATE_FORMAT(odr.OrderDate,'%H:%i') as orderdate, " +
                    "odr.guests, " +
                    "odr.waiter, " +
                    "w.shortname, " +
                    "odr.ordertable " +
                    "from orders odr " +
                    "left join waitress w on odr.waiter = w.id " +
                    "where odr.status = 0 and odr.Waiter = " + cafe.getUserid() +
                    " order by odr.OrderTable";

            HTTPRequest httpRequest = new HTTPRequest(OrderActivity.this, url, query);
            return httpRequest.getJson();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            for(int i=0; i<=jsonArray.length(); i++) {
                try {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    Table table = new Table(OrderActivity.this);
                    table.setTableid(jo.getInt("ordertable"));
                    table.setOid(jo.getInt("id"));
                    table.setWname(jo.getString("shortname"));
                    table.setTime_text(jo.getString("orderdate"));
                    table.setUid(jo.getInt("waiter"));
                    table.setGuests(jo.getInt("guests"));
                    table.setTagg(jo.getInt("id"));
                    fl.addView(table);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/*
            int cjson = 0;
            for(int i=1; i<=20; i++) {

                Table table = new Table(OrderActivity.this);
                table.setTableid(i);
                table.setUid(0);

                try {
                    JSONObject jo = jsonArray.getJSONObject(cjson);

                    if(cjson < jsonArray.length() && jo.getInt("ordertable") == i) {
                        table.setTableid(jo.getInt("ordertable"));
                        table.setOid(jo.getInt("id"));
                        table.setWname(jo.getString("shortname"));
                        table.setTime_text(jo.getString("orderdate"));
                        table.setUid(jo.getInt("waiter"));
                        table.setGuests(jo.getInt("guests"));
                        table.setTagg(jo.getInt("id"));
                        cjson++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fl.addView(table);
            }
*/
