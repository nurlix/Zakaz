package com.nurlan.zakaz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.nurlan.zakaz.Adapters.OrderItemAdapter;
import com.nurlan.zakaz.Adapters.ProductsAdapter;
import com.nurlan.zakaz.DBHelpers.DataProvider;
import com.nurlan.zakaz.Objects.Order;
import com.nurlan.zakaz.Objects.OrderItem;
import com.nurlan.zakaz.android.Dialog;
import com.nurlan.zakaz.android.MenuLayout;

public class OrderViewActivity extends Activity {

    private MenuLayout menuLayout;

    private final int FGNONE = 1;
    private final int FGINSERT = 2;
    private final int FGUPDATE = 3;

    private ProductsAdapter productsAdapter;
    private OrderItemAdapter orderItemAdapter;

    ListView orderItemList;
    TextView Total;
    Button addButton;

    private ExpandableListView elvProducts;

    int ItemPosition;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuLayout = (MenuLayout) getLayoutInflater().inflate(R.layout.activity_order_view, null);
        setContentView(menuLayout);

        Total = (TextView)findViewById(R.id.overallTotal);
        elvProducts = (ExpandableListView)findViewById(R.id.elvProducts);

        //Load Products
        DataProvider products = new DataProvider(this);
        productsAdapter = new ProductsAdapter(OrderViewActivity.this, products.GETCATEGORIES());
        elvProducts.setAdapter(productsAdapter);
        elvProducts.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                OrderItem oi = new OrderItem();
                oi.setItemId(productsAdapter.getChild(groupPosition, childPosition).getId());
                oi.setItem(productsAdapter.getChild(groupPosition, childPosition).getItem());
                oi.setPrice(productsAdapter.getChild(groupPosition, childPosition).getPrice());
                search(oi);
                return false;
            }
        });

        elvProducts.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    elvProducts.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        //Load OrderItems
        orderItemList =(ListView)findViewById(R.id.orderItemList);
        orderItemList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        order = (Order) getIntent().getSerializableExtra("order");

        setTitle(order.getOrderCustomer());

        if(order.getID() != 0) {
            DataProvider orderItems = new DataProvider(this);
            orderItemAdapter = new OrderItemAdapter(OrderViewActivity.this, orderItems.GETDETAILS(order.getOrderID()));
        } else {
            orderItemAdapter = new OrderItemAdapter(OrderViewActivity.this);
        }

        orderItemList.setAdapter(orderItemAdapter);
        Total.setText("Итого : " + OverAllTotal());
        orderItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemPosition = position;
                Intent intent = new Intent(OrderViewActivity.this, NumpadActivity.class);
                intent.putExtra("Title", orderItemAdapter.getItem(position).getItem());
                startActivityForResult(intent, 1);
            }
        });

        addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLayout.toggleMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_orderview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        DataProvider db = new DataProvider(OrderViewActivity.this);
        switch (item.getItemId()) {
            case R.id.action_Save:
                if (orderItemAdapter.getCount() != 0) {
                    db.AddOrder(order);
                    for (OrderItem oi : orderItemAdapter.getAll()) {
                        db.AddOrderItem(oi);
                    }
                    orderItemAdapter.clear();
                    Intent intent = new Intent(OrderViewActivity.this, OrderActivity.class);
                    startActivity(intent);
                } else {
                    Dialog dialog = new Dialog(OrderViewActivity.this, "Внимание", "Список пуст!", 1);
                }
                return true;
            case R.id.action_Close:
                /*
                if ((orderItemAdapter.getCount() != 0) && (!order.isSaved())) {
                    Dialog dialog = new Dialog(OrderViewActivity.this, "Внимание", "Сохранить изменения!", 2);
                    if (dialog.isResult()) {
                        db.AddOrder(order);
                        for (OrderItem oi : orderItemAdapter.getAll()) {
                            db.AddOrderItem(oi);
                        }
                        orderItemAdapter.clear();
                    }
                    Intent intent = new Intent(OrderViewActivity.this, OrderActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(OrderViewActivity.this, OrderActivity.class);
                    startActivity(intent);
                }
                */
                Intent intent = new Intent(OrderViewActivity.this, OrderActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void search(OrderItem oi){
        int size = orderItemAdapter.getCount();
        int i = 0;
        if(size != 0)
            for(OrderItem o : orderItemAdapter.getAll()){
                if(o.getItemId() == oi.getItemId()) {
                    ItemPosition = i;
                    return;
                }
                i++;
            }

        ItemPosition = -1;

        oi.setOrderID(order.getOrderID());
        oi.setFlag(FGINSERT);
        orderItemAdapter.addItem(oi);
        orderItemAdapter.notifyDataSetChanged();

        Intent intent = new Intent(OrderViewActivity.this, NumpadActivity.class);
        intent.putExtra("Title", oi.getItem());
        startActivityForResult(intent, 1);
    }

    int OverAllTotal() {
        int Total = 0;
        for(int i=0; i < orderItemAdapter.getCount(); i++){
            Total = Total + Math.round(orderItemAdapter.getItem(i).getTotal());
        }
        return Total;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if(ItemPosition != -1) {
                orderItemAdapter.getItem(ItemPosition).setQuantity(data.getIntExtra("result", 0));
                if (orderItemAdapter.getItem(ItemPosition).getFlag() == FGNONE)
                    orderItemAdapter.getItem(ItemPosition).setFlag(FGUPDATE);
                orderItemAdapter.notifyDataSetChanged();
            } else {
                orderItemAdapter.getItem(orderItemAdapter.getCount() - 1).setQuantity(data.getIntExtra("result", 0));
                if (orderItemAdapter.getItem(orderItemAdapter.getCount() - 1).getFlag() == FGNONE)
                    orderItemAdapter.getItem(orderItemAdapter.getCount() - 1).setFlag(FGUPDATE);
                orderItemAdapter.notifyDataSetChanged();
            }
        }
        Total.setText("Итого : " + OverAllTotal());
        order.setSaved(false);
        if(menuLayout.getMenuState() == MenuLayout.MenuState.OPEN)
            menuLayout.toggleMenu();
    }

    @Override
    public void onBackPressed() {
        if(menuLayout.getMenuState() == MenuLayout.MenuState.OPEN)
            menuLayout.toggleMenu();
    }

    public void toggleMenu(View v) {
        menuLayout.toggleMenu();
    }
}
