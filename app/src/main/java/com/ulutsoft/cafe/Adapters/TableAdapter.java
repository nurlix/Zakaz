package com.ulutsoft.cafe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ulutsoft.cafe.Objects.Table;
import com.ulutsoft.cafe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURLAN on 21.11.2015.
 */
public class TableAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Table> tables;
    List<Table> tableList = null;
    static LayoutInflater inflater = null;

    public TableAdapter(Context context, List<Table> tables) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.tables = new ArrayList<>();
        this.tables.addAll(tables);
        tableList = tables;
    }

    private static class ViewHolder {
        TextView TableName;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.table, parent, false);
            holder = new ViewHolder();
            holder.TableName = (TextView)convertView.findViewById(R.id.tvTableName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.TableName.setText(tables.get(position).getTableName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return tables.size();
    }

    @Override
    public Table getItem(int position) {
        return tables.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void filter(int rid){

        tables.clear();
        for(Table t : tableList){
            if (rid == t.getRoomID()) {
                tables.add(t);
            }
        }
        notifyDataSetChanged();
    }
}
