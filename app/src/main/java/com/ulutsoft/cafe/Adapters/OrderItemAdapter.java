package com.ulutsoft.cafe.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ulutsoft.cafe.Objects.OrderItem;
import com.ulutsoft.cafe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURLAN on 14.07.2015.
 */
public class OrderItemAdapter extends BaseAdapter {

    final int FGNONE = 0;

    Context mContext;
    ArrayList<OrderItem> data;
    static LayoutInflater inflater = null;

    public OrderItemAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        data = new ArrayList<OrderItem>();
    }

    public OrderItemAdapter(Context context, List<OrderItem> orderItems) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        data = new ArrayList<>();
        data.addAll(orderItems);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public OrderItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean areAllItemsEnabled() { return false; }

    @Override
    public boolean isEnabled(int position)
    {
        return getItem(position).getFlag() == FGNONE ? false : true;
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
            holder.Quantity = (TextView)convertView.findViewById(R.id.txtQuantity);
            holder.Item = (TextView)convertView.findViewById(R.id.txtItemName);
            holder.Total = (TextView)convertView.findViewById(R.id.txtTotal);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.Item.setText(data.get(position).getItem());
        holder.Quantity.setText(data.get(position).getQuantity() + "");
        holder.Total.setText(data.get(position).getTotal() + "");

        if(data.get(position).getFlag() == FGNONE){
            holder.Item.setTextColor(Color.LTGRAY);
            holder.Total.setEnabled(false);
        } else {
            holder.Item.setTextColor(Color.BLACK);
            holder.Total.setEnabled(true);
            holder.Total.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float qty = getItem(position).getQuantity();
                    if (qty > 1) {
                        getItem(position).setQuantity(qty - 1);
                    } else {
                        removeItem(position);
                    }
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }

    void removeItem(int position){
        data.remove(data.get(position));
    }

    public ArrayList<OrderItem> getAll(){
        return data;
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    public void addItem(OrderItem oi){
        data.add(oi);
    }
}
