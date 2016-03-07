package com.nurlan.zakaz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nurlan.zakaz.Objects.Customer;
import com.nurlan.zakaz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURLAN on 30.07.2015.
 */

public class CustomerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Customer> customers;
    private static LayoutInflater inflater = null;

    public CustomerAdapter(Context context, List<Customer> customers) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.customers = new ArrayList<>();
        this.customers.addAll(customers);
    }

    private static class ViewHolder {
        TextView Name;
        TextView Phone;
        TextView Adres;
    }

    @Override
    public int getCount() {
        return customers.size();
    }

    @Override
    public Customer getItem(int position) {
        return customers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.customer, parent, false);
            holder = new ViewHolder();
            holder.Name = (TextView)convertView.findViewById(R.id.CustomerName);
            holder.Phone = (TextView)convertView.findViewById(R.id.CustomerPhone);
            holder.Adres = (TextView)convertView.findViewById(R.id.CustomerAdres);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.Name.setText(customers.get(position).getName());
        holder.Phone.setText(customers.get(position).getPhone());
        holder.Adres.setText(customers.get(position).getAdress());

        return convertView;
    }
}






