package com.nurlan.zakaz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nurlan.zakaz.Adapters.CustomerAdapter;
import com.nurlan.zakaz.DBHelpers.DataProvider;
import com.nurlan.zakaz.Objects.Order;

public class CustomerActivity extends Activity {

    private ListView customerlist;
    private CustomerAdapter customerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        DataProvider customer = new DataProvider(this);
        customerAdapter = new CustomerAdapter(CustomerActivity.this, customer.GETCUSTOMERS());
        customerlist = (ListView) findViewById(R.id.customerlist);
        customerlist.setAdapter(customerAdapter);
        customerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = new Order();
                order.setOrderCustomer(customerAdapter.getItem(position).getName());
                order.setCustomerId(customerAdapter.getItem(position).getId());

                Intent intent = new Intent(CustomerActivity.this, OrderViewActivity.class);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });

        customerlist.setFastScrollEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CustomerActivity.this, OrderActivity.class);
        startActivity(intent);
    }
}
