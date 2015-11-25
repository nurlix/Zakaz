package com.ulutsoft.cafe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ulutsoft.cafe.Objects.User;
import com.ulutsoft.cafe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURLAN on 30.07.2015.
 */
public class UserAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<User> users;
    private static LayoutInflater inflater = null;

    public UserAdapter(Context context, List<User> users) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.users = new ArrayList<>();
        this.users.addAll(users);
    }

    private static class ViewHolder {
        TextView userName;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.user, parent, false);
            holder = new ViewHolder();
            holder.userName = (TextView)convertView.findViewById(R.id.username);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.userName.setText(users.get(position).getUsername());

        return convertView;
    }
}






