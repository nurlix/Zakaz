package com.ulutsoft.nurlan.dsclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURLAN on 11.07.2015.
 */
public class TableAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Table> tables;
    private static LayoutInflater inflater = null;

    public TableAdapter(Context context, List<Table> tables){
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.tables = new ArrayList<>();
        this.tables.addAll(tables);
    }

    private static class ViewHolder {
        TextView TableName;
        TextView Waiter;
        TextView Time;
        TextView Guests;
    }

    @Override
    public int getCount() {
        return tables.size();
    }

    @Override
    public Object getItem(int position) { return position; }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.table, parent, false);
            holder = new ViewHolder();
            holder.TableName = (TextView)convertView.findViewById(R.id.table_name);
            holder.Waiter = (TextView)convertView.findViewById(R.id.wname);
            holder.Time = (TextView)convertView.findViewById(R.id.time_text);
            holder.Guests = (TextView)convertView.findViewById(R.id.guest_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.TableName.setText(tables.get(position).getTableName());
        holder.Waiter.setText(tables.get(position).getWaiter());
        holder.Time.setText(tables.get(position).getTime());
        holder.Guests.setText(tables.get(position).getGuests() + " guests");

        return convertView;
    }
}
