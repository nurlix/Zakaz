package com.ulutsoft.cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ulutsoft.cafe.Adapters.OrderItemAdapter;
import com.ulutsoft.cafe.Adapters.ProductAdapter;
import com.ulutsoft.cafe.Objects.Order;
import com.ulutsoft.cafe.Objects.OrderItem;
import com.ulutsoft.cafe.Objects.Product;
import com.ulutsoft.cafe.android.Dialog;
import com.ulutsoft.cafe.android.HTTPRequest;
import com.ulutsoft.cafe.android.MenuLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderViewActivity extends Activity {

    Cafe cafe;

    final int FGNONE = 0;
    final int FGINSERT = 1;

    ArrayList<Product> products = new ArrayList<Product>();
    ArrayList<OrderItem> orderItems = new ArrayList<>();
    ProductAdapter productAdapter;
    OrderItemAdapter orderItemAdapter;
    ListView orderlist;

    Button btnBack;
    ListView picklist;
    TextView orderTitle;

    String query;
    Integer orderid;

    LoadMenuItems loadMenuItems;
    LoadOrder loadOrder;
    UpdateOrder updateOrder;
    PrintReceipt printReceipt;

    Order order;

    MenuLayout menuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuLayout = (MenuLayout) getLayoutInflater().inflate(R.layout.activity_orderview_sm, null);
        setContentView(menuLayout);

        cafe = (Cafe)getApplicationContext();
        order = cafe.getOrder();

        orderTitle = (TextView)findViewById(R.id.txtOrderTitle);

       //OrderItems
        orderItemAdapter = new OrderItemAdapter(OrderViewActivity.this);
        orderlist =(ListView)findViewById(R.id.orderItemList);
        orderlist.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        orderlist.setAdapter(orderItemAdapter);
        orderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    orderItemAdapter.getItem(position).setQuantity(orderItemAdapter.getItem(position).getQuantity() + 1);
                    orderItemAdapter.notifyDataSetChanged();
            }
        });

        //PicklList
        picklist = (ListView) findViewById(R.id.picklist);
        picklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pid = 0;
                if (productAdapter.getItem(position).isCategory()) {
                    pid = productAdapter.getItem(position).getId();
                    productAdapter.filter(pid);
                } else {
                    OrderItem oi = new OrderItem();
                    oi.setItemId(productAdapter.getItem(position).getId());
                    oi.setItem(productAdapter.getItem(position).getItem());
                    oi.setPrice(productAdapter.getItem(position).getPrice());
                    search(oi);
                }
            }
        });

        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productAdapter.filter(0);
            }
        });

        //Load Menu
        query = getString(R.string.qry_loadMenu);
        loadMenuItems = new LoadMenuItems();
        loadMenuItems.execute(cafe.getServer(), query);

        //Load Order
        orderTitle.setText("Зал " + order.getRoomname() + "\nСтол №: " + order.getOrderTable() + " ( Количество гостей : " + order.getGuests() + " )");
        orderid = order.getOrderID();
        if(orderid != 0) {
            query = getString(R.string.qry_loadOrderItem, orderid );
            loadOrder = new LoadOrder();
            loadOrder.execute(cafe.getServer(), query);
        }
    }

    public void toggleMenu(View v) {
        menuLayout.toggleMenu();
        productAdapter.filter(0);
    }

    void search(OrderItem oi){

        int size = orderItemAdapter.getCount();
        int i = 0;
        if(size != 0)
            for(OrderItem o : orderItemAdapter.getAll()){
                if((o.getItemId() == oi.getItemId()) && (o.getFlag() != FGNONE)){
                    orderItemAdapter.getItem(i).setQuantity(orderItemAdapter.getItem(i).getQuantity() + 1);
                    orderItemAdapter.notifyDataSetChanged();

                    Toast toast = Toast.makeText(this, orderItemAdapter.getItem(i).getItem() + " x " + orderItemAdapter.getItem(i).getQuantity(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();

                    return;
                }
                i++;
            }

        oi.setFlag(FGINSERT);
        orderItemAdapter.addItem(oi);
        orderItemAdapter.notifyDataSetChanged();

        Toast toast = Toast.makeText(this, orderItemAdapter.getItem(i).getItem() + " x " + orderItemAdapter.getItem(i).getQuantity(), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_ConfirmOrder : {
                if (orderItemAdapter.getCount() != 0) {
                    List<OrderItem> oi = orderItemAdapter.getAll();
                    order.setOrderItems(oi);
                    query = order.SaveOrder();
                    updateOrder = new UpdateOrder();
                    updateOrder.execute(cafe.getServer(), query, "1");
                } else {
                    Dialog dialog = new Dialog(OrderViewActivity.this, "Внимание", "Список пуст!", 1);
                }
            } break;
            case R.id.action_CancelOrder : {
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
            } break;
            case R.id.action_setGuests : {
                Intent intent = new Intent(this, NumpadActivity.class);
                startActivityForResult(intent, 1);
            } break;
            case R.id.action_PrintReceipt : {
                query = "order:"+Integer.toString(order.getOrderID());
                printReceipt = new PrintReceipt();
                printReceipt.execute(cafe.getServer(), query);
                Toast.makeText(OrderViewActivity.this, "Печать чека", Toast.LENGTH_SHORT).show();
            } break;
            case R.id.action_CloseOrder : {
                query = order.CloseOrder();
                updateOrder = new UpdateOrder();
                updateOrder.execute(cafe.getServer(), query, "1");
                Toast.makeText(OrderViewActivity.this, "Стол " + order.getOrderTable() + " закрыт", Toast.LENGTH_LONG).show();
                //Print receipt
                query = "order:" + Integer.toString(order.getOrderID());
                printReceipt = new PrintReceipt();
                printReceipt.execute(cafe.getServer(), query);
            } break;
            case R.id.action_Move : {
                Intent intent = new Intent(this, NewOrderActivity.class);
                intent.putExtra("hide", true);
                startActivityForResult(intent, 2);
            } break;
        }
        return super.onOptionsItemSelected(item);
    }

    void printReceipts(int OID) {
        query = "print:" + OID;
        printReceipt = new PrintReceipt();
        printReceipt.execute(cafe.getServer(), query);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == 1 & data.getIntExtra("result", 0) != 0){
                order.setGuests(data.getIntExtra("result", 0));

                updateOrder = new UpdateOrder();
                updateOrder.execute(cafe.getServer(), "update orders set guests = " + order.getGuests() + " where ID = " + order.getOrderID(), "");

            } else if((requestCode == 2) & (resultCode == Activity.RESULT_OK)){

                order.setOrderRoom(data.getIntExtra("room", 0));
                order.setOrderTable(data.getIntExtra("table", 0));
                order.setRoomname(data.getStringExtra("roomname"));

                updateOrder = new UpdateOrder();
                updateOrder.execute(cafe.getServer(), "update orders set ordertable = " + order.getOrderTable() + ", OrderRoom = " + order.getOrderRoom() + " where ID = " + order.getOrderID() , "");
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(menuLayout.getMenuState() == MenuLayout.MenuState.OPEN) menuLayout.toggleMenu();
    }


    /////////////////////////// ASYNCTASK ///////////////////////////////////////////////

    class LoadMenuItems extends AsyncTask<String, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(String... params) {
            HTTPRequest httpRequest = new HTTPRequest(OrderViewActivity.this, params[0], params[1]);
            return httpRequest.getJson();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if(jsonArray != null) {
                JSONArray json = jsonArray;
                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jo = json.getJSONObject(i);
                        Product p = new Product(
                                jo.getInt("ID"),
                                jo.getInt("parent"),
                                jo.getInt("category"),
                                jo.getString("Item"),
                                (float) jo.getDouble("Price")
                        );
                        products.add(p);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                productAdapter = new ProductAdapter(OrderViewActivity.this, products);
                picklist.setAdapter(productAdapter);
                productAdapter.filter(0);
            }
        }
    }

    class LoadOrder extends AsyncTask<String, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(String... params) {
            HTTPRequest httpRequest = new HTTPRequest(OrderViewActivity.this, params[0], params[1]);
            return httpRequest.getJson();
        }

        @Override
        protected void onPostExecute(JSONArray json) {
            if(json != null) {
                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jo = json.getJSONObject(i);
                        OrderItem oi = new OrderItem();
                        oi.setOrderItemID(jo.getInt("id"));
                        oi.setOrderID(jo.getInt("orderid"));
                        oi.setItemId(jo.getInt("item"));
                        oi.setItem(jo.getString("name"));
                        oi.setInitialQuantity(jo.getInt("quantity"));
                        oi.setQuantity(jo.getInt("quantity"));
                        oi.setPrice((float) jo.getDouble("price"));
                        oi.setFlag(FGNONE);
                        orderItems.add(oi);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                orderItemAdapter = new OrderItemAdapter(OrderViewActivity.this, orderItems);
                orderlist.setAdapter(orderItemAdapter);
            }
        }
    }

    class UpdateOrder extends AsyncTask<String, Void, JSONArray>{

        String print = "";

        @Override
        protected JSONArray doInBackground(String... params) {
            print = params[2];
            HTTPRequest httpRequest = new HTTPRequest(OrderViewActivity.this, params[0], params[1]);
            return httpRequest.getJson();
        }

        @Override
        protected void onPostExecute(JSONArray json) {
            int OID = 0;
            if(json != null){
                try {
                    JSONObject jo = json.getJSONObject(0);
                    OID = jo.getInt("Result");
                    if(print != "") {
                        printReceipts(OID);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            orderItemAdapter.clear();
            Intent intent = new Intent(OrderViewActivity.this, OrderActivity.class);
            startActivity(intent);
        }
    }

    class PrintReceipt extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            HTTPRequest httpRequest = new HTTPRequest(OrderViewActivity.this, params[0], params[1]);
            return null;
        }
    }
}
