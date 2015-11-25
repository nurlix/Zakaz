package com.ulutsoft.cafe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ulutsoft.cafe.Objects.Product;
import com.ulutsoft.cafe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURLAN on 03.07.2015.
 */

public class ProductAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Product> data;
    private List<Product> list = null;
    private static LayoutInflater inflater = null;

    public ProductAdapter(Context context, List<Product> products) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        data = new ArrayList<>();
        data.addAll(products);
        list = products;
    }

    private static class ViewHolder {
        TextView item;
        TextView price;
        ImageView img;
    }

    public int getCount() {
        return data.size();
    }

    public Product getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.product, parent, false);
            holder = new ViewHolder();
            holder.item = (TextView)convertView.findViewById(R.id.item);
            holder.price = (TextView)convertView.findViewById(R.id.price);
            holder.img = (ImageView)convertView.findViewById(R.id.ci);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.item.setText(data.get(position).getItem());
        if(data.get(position).isCategory()) {
            holder.price.setText("");
            holder.img.setImageResource(R.drawable.ic_cr);
        }
        else
        {
            holder.price.setText(data.get(position).getPrice() + mContext.getString(R.string.som));
            holder.img.setImageResource(0);
        }

        return convertView;
    }

    public void filter(int pid){

        data.clear();
        for(Product p : list){
            if (pid == p.getParent()) {
                data.add(p);
            }
        }
        notifyDataSetChanged();
    }
}
