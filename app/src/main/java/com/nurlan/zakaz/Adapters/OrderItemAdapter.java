package com.nurlan.zakaz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nurlan.zakaz.Objects.OrderItem;
import com.nurlan.zakaz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURLAN on 14.07.2015.
 */

public class OrderItemAdapter extends BaseAdapter {

    final int FGNONE = 1;
    final int FGUPDATE = 3;

    Context mContext;
    ArrayList<OrderItem> orderItems;
    static LayoutInflater inflater = null;

    public OrderItemAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.orderItems = new ArrayList<>();
    }

    public OrderItemAdapter(Context context, List<OrderItem> orderItems) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.orderItems = new ArrayList<>();
        this.orderItems.addAll(orderItems);
    }

    @Override
    public int getCount() {
        return orderItems.size();
    }

    @Override
    public OrderItem getItem(int position) {
        return orderItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView Item;
        TextView Quantity;
        TextView Total;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.order_item, parent, false);
            holder = new ViewHolder();
            holder.Item = (TextView)convertView.findViewById(R.id.txtItemName);
            holder.Quantity = (TextView)convertView.findViewById(R.id.txtQuantity);
            holder.Total = (TextView)convertView.findViewById(R.id.txtTotal);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.Quantity.setText(orderItems.get(position).getQuantity() + "");
        holder.Item.setText(orderItems.get(position).getItem());
        holder.Total.setText("" + Math.round(orderItems.get(position).getTotal()));

        return convertView;
    }

    void removeItem(int position){
        orderItems.get(position).setQuantity(0);
    }

    public ArrayList<OrderItem> getAll(){
        return orderItems;
    }

    public void clear(){
        orderItems.clear();
        notifyDataSetChanged();
    }

    public void addItem(OrderItem oi){
        orderItems.add(oi);
    }
}
