package com.ulutsoft.cafe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ulutsoft.cafe.Objects.Order;
import com.ulutsoft.cafe.R;

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
        TextView orderTable;
        TextView orderTime;
        TextView orderGuests;
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
            holder.orderTable = (TextView)convertView.findViewById(R.id.orderTable);
            holder.orderTime = (TextView)convertView.findViewById(R.id.orderTime);
            holder.orderGuests = (TextView)convertView.findViewById(R.id.orderGuests);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.orderTable.setText(orders.get(position).getRoomname() + " - Стол " + Integer.toString(orders.get(position).getOrderTable()));
        holder.orderTime.setText(orders.get(position).getOrderDate());
        holder.orderGuests.setText("гости : " + Integer.toString(orders.get(position).getGuests()));

        return convertView;
    }
}
