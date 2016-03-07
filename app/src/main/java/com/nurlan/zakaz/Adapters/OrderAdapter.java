package com.nurlan.zakaz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nurlan.zakaz.Objects.Order;
import com.nurlan.zakaz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURLAN on 09/10/2015.
 */
public class OrderAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Order> orders;
    private static LayoutInflater inflater = null;

    public OrderAdapter(Context context, List<Order> orders) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.orders = new ArrayList<>();
        this.orders.addAll(orders);
    }

    private static class ViewHolder {
        TextView orderCustomer;
        TextView orderDate;
        TextView orderTotal;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Order getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.order, parent, false);
            holder = new ViewHolder();
            holder.orderCustomer = (TextView)convertView.findViewById(R.id.orderCustomer);
            holder.orderDate = (TextView)convertView.findViewById(R.id.orderDate);
            holder.orderTotal = (TextView)convertView.findViewById(R.id.orderTotal);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.orderCustomer.setText(orders.get(position).getOrderCustomer());
        holder.orderDate.setText(orders.get(position).getOrderDate().toString());
        holder.orderTotal.setText("Сумма : " + orders.get(position).getTotal());

        return convertView;
    }
}
