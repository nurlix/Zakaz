package com.ulutsoft.cafe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ulutsoft.cafe.Objects.Room;
import com.ulutsoft.cafe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURLAN on 21.11.2015.
 */
public class RoomAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Room> rooms;
    static LayoutInflater inflater = null;

    public RoomAdapter(Context context, List<Room> rooms) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.rooms = new ArrayList<>();
        this.rooms.addAll(rooms);
    }

    private static class ViewHolder {
        TextView RoomName;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.room, parent, false);
            holder = new ViewHolder();
            holder.RoomName = (TextView)convertView.findViewById(R.id.tvRoomName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.RoomName.setText(rooms.get(position).getRoomName());

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
        return rooms.size();
    }

    @Override
    public Room getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
