package com.nurlan.zakaz;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nurlan.zakaz.Adapters.OrderAdapter;
import com.nurlan.zakaz.DBHelpers.DataProvider;
import com.nurlan.zakaz.Objects.Order;
import com.nurlan.zakaz.Objects.OrderItem;
import com.nurlan.zakaz.android.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderActivity extends Activity {

    ListView orderList;

    OrderAdapter orderAdapter;
    UploadOrders uploadOrders;
    Zakaz zakaz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        zakaz = (Zakaz) getApplicationContext();

        DataProvider db = new DataProvider(this);
        orderAdapter = new OrderAdapter(OrderActivity.this, db.GETORDERS());
        orderList = (ListView) findViewById(R.id.orderlist);
        orderList.setAdapter(orderAdapter);
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrderActivity.this, OrderViewActivity.class);
                intent.putExtra("order", orderAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() { }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_orders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.order_new:
                Intent intent = new Intent(OrderActivity.this, CustomerActivity.class);
                startActivity(intent);
                return true;
            case R.id.order_upload:
                DataProvider db = new DataProvider(OrderActivity.this);
                if(db.Count("orders") != 0) {
                    uploadOrders = new UploadOrders();
                    uploadOrders.execute(zakaz.getServer());
                } else {
                    Toast.makeText(OrderActivity.this, "Список пуст!", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class UploadOrders extends AsyncTask<String, Void, Void> {

        DataProvider db = new DataProvider(OrderActivity.this);

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(OrderActivity.this, "Выгрузка завершена!", Toast.LENGTH_LONG).show();
            db.resetOrders();
            db.close();
            finish();
            startActivity(getIntent());
        }

        @Override
        protected Void doInBackground(String... params) {

            SQLConnection httpRequest = new SQLConnection(params[0]);
            String sqlodr = "INSERT INTO orders(Customer, Agent, OrderDate, Discount, OrderID) VALUES(?, ?, ?, ?, ?)";
            String sqlodl = "INSERT INTO details(OrderID, Item, Quantity, Price, Discount) VALUES(?, ?, ?, ?, ?)";
            Connection con = null;
            PreparedStatement prepared = null;

            try {
                con = httpRequest.CON();
                if (con != null) {
                    List<Order> orders = db.ORDERS(zakaz.getAgent());
                    prepared = con.prepareStatement(sqlodr);
                    for(Order o : orders) {
                        prepared.setInt(1, o.getCustomerId());
                        prepared.setInt(2, o.getAgent());
                        prepared.setString(3, o.getOrderDate());
                        prepared.setFloat(4, o.getOrderDiscount());
                        prepared.setString(5, o.getOrderID());
                        prepared.addBatch();
                        prepared.executeUpdate();
                    }

                    List<OrderItem> details = db.DETAILS();
                    prepared = con.prepareStatement(sqlodl);
                    for(OrderItem d : details) {
                        prepared.setString(1, d.getOrderID());
                        prepared.setInt(2, d.getItemId());
                        prepared.setFloat(3, d.getQuantity());
                        prepared.setFloat(4, d.getPrice());
                        prepared.setFloat(5, d.getDiscount());
                        prepared.addBatch();
                        prepared.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (prepared != null) prepared.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
            return null;
        }
    }
}

