package com.nurlan.zakaz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.nurlan.zakaz.Objects.Category;
import com.nurlan.zakaz.Objects.Product;
import com.nurlan.zakaz.R;

import java.util.ArrayList;
import java.util.List;

/*
	Created by NURLAN on 18.12.2015.
*/

public class ProductsAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Category> categories;
    private int lastExpandedGroupPosition;

    public ProductsAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = new ArrayList<>();
        this.categories.addAll(categories);
    }

    private static class GroupViewHolder {
        TextView item;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if(convertView == null) {
            LayoutInflater groupinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = groupinflater.inflate(R.layout.product_group, parent, false);
            holder = new GroupViewHolder();
            holder.item = (TextView)convertView.findViewById(R.id.categories);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder)convertView.getTag();
        }

        holder.item.setText(categories.get(groupPosition).getName());
        return convertView;
    }

    private static class ChildViewHolder {
        TextView item;
        TextView price;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;

        if(convertView == null) {
            LayoutInflater childinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = childinflater.inflate(R.layout.product, parent, false);
            holder = new ChildViewHolder();
            holder.item = (TextView)convertView.findViewById(R.id.item);
            holder.price = (TextView)convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder)convertView.getTag();
        }

        holder.item.setText(categories.get(groupPosition).getItems().get(childPosition).getItem());
        holder.price.setText(categories.get(groupPosition).getItems().get(childPosition).getPrice() + " с");
        return convertView;
    }

    @Override
    public Category getGroup(int groupPosition) {
        return categories.get(groupPosition);
    }

    @Override
    public Product getChild(int groupPosition, int childPosition) {
        return categories.get(groupPosition).getItems().get(childPosition);
    }

    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return categories.get(groupPosition).getItems().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

