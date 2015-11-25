package com.ulutsoft.cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ulutsoft.cafe.Adapters.OrderAdapter;
import com.ulutsoft.cafe.Objects.Order;
import com.ulutsoft.cafe.android.HTTPRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderActivity extends Activity {

    LoadOrders loadOrders;

    ListView orderList;
    ArrayList<Order> orders = new ArrayList<>();
    OrderAdapter orderAdapter;

    Cafe cafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        cafe = (Cafe)getApplicationContext();

        orderList = (ListView)findViewById(R.id.orderlist);
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(orderAdapter.getItem(position).getOrderStatus() != 2) {
                    cafe.setOrder(orderAdapter.getItem(position));
                    Intent intent = new Intent(OrderActivity.this, OrderViewActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(OrderActivity.this, "Стол закрыт", Toast.LENGTH_LONG).show();
                }
            }
        });

        String query = getString(R.string.qry_loadOrder, cafe.getUserid());

        loadOrders = new LoadOrders();
        loadOrders.execute(cafe.getServer(), query);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_new_order, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Order order = new Order();
            order.setOrderRoom(data.getIntExtra("room", 0));
            order.setOrderTable(data.getIntExtra("table", 0));
            order.setRoomname(data.getStringExtra("roomname"));
            order.setWaiter(cafe.getUserid());
            order.setGuests(data.getIntExtra("guests", 1));
            cafe.setOrder(order);
            Intent intent = new Intent(this, OrderViewActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent np = new Intent(this, NewOrderActivity.class);
        startActivityForResult(np, 1);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, UsersActivity.class);
        startActivity(intent);
    }

    class LoadOrders extends AsyncTask<String, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(String... params) {
            HTTPRequest httpRequest = new HTTPRequest(OrderActivity.this, params[0], params[1]);
            return httpRequest.getJson();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if(jsonArray != null) {
                for(int i=0; i<=jsonArray.length(); i++) {
                    try {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        Order o = new Order(
                                jo.getInt("id"),
                                jo.getString("orderdate"),
                                jo.getInt("ordertable"),
                                jo.getInt("waiter"),
                                jo.getInt("guests"),
                                jo.getString("room"),
                                (byte)jo.getInt("status")
                        );
                        orders.add(o);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                orderAdapter = new OrderAdapter(OrderActivity.this, orders);
                orderList.setAdapter(orderAdapter);
            }
        }
    }
}

