package com.nurlan.zakaz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nurlan.zakaz.Objects.Product;
import com.nurlan.zakaz.R;

import java.util.ArrayList;
import java.util.List;

/*
Created by NURLAN on 30.11.2015.
*/

public class ProductAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Product> products;
    private List<Product> list = null;
    private static LayoutInflater inflater = null;

    public ProductAdapter(Context context, List<Product> products) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.products = new ArrayList<>();
        this.products.addAll(products);
        list = products;
    }

    private static class ViewHolder {
        TextView item;
        TextView price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.product, parent, false);
            holder = new ViewHolder();
            holder.item = (TextView)convertView.findViewById(R.id.item);
            holder.price = (TextView)convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.item.setText(products.get(position).getItem());
        holder.price.setText(products.get(position).getPrice() + mContext.getString(R.string.som));

        return convertView;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void filter(int pid){
        products.clear();
        for(Product product : list){
            if (product.getParent() == pid) {
                products.add(product);
            }
        }
        notifyDataSetChanged();
    }
}
