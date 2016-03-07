package com.nurlan.zakaz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nurlan.zakaz.Objects.Agent;
import com.nurlan.zakaz.Objects.Customer;
import com.nurlan.zakaz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURLAN on 17.12.2015.
 */
public class AgentAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Agent> agents;
    private static LayoutInflater inflater = null;

    public AgentAdapter(Context context, List<Agent> agents) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.agents = new ArrayList<>();
        this.agents.addAll(agents);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.agent, parent, false);
            holder = new ViewHolder();
            holder.Agent = (TextView)convertView.findViewById(R.id.agentName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.Agent.setText(agents.get(position).getName());
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
        return agents.size();
    }

    @Override
    public Agent getItem(int position) {
        return agents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView Agent;
    }
}
