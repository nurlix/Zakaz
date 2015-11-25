package com.ulutsoft.nurlan.dsclient.android;

import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

/**
 * Created by NURLAN on 10.07.2015.
 */
public class TreeViewItemClickListener implements AdapterView.OnItemClickListener {

    private TreeViewAdapter treeViewAdapter;

    public TreeViewItemClickListener(TreeViewAdapter treeViewAdapter) {
        this.treeViewAdapter = treeViewAdapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Node element = (Node) treeViewAdapter.getItem(position);

        ArrayList<Node> elements = treeViewAdapter.getElements();

        ArrayList<Node> elementsData = treeViewAdapter.getElementsData();


        if (!element.isHasChildren()) {
            return;
        }

        if (element.isExpanded()) {
            element.setExpanded(false);

            ArrayList<Node> elementsToDel = new ArrayList<Node>();
            for (int i = position + 1; i < elements.size(); i++) {
                if (element.getLevel() >= elements.get(i).getLevel())
                    break;
                elementsToDel.add(elements.get(i));
            }
            elements.removeAll(elementsToDel);
            treeViewAdapter.notifyDataSetChanged();
        } else {
            element.setExpanded(true);

            int i = 1;
            for (Node e : elementsData) {
                if (e.getParendId() == element.getId()) {
                    e.setExpanded(false);
                    elements.add(position + i, e);
                    i ++;
                }
            }
            treeViewAdapter.notifyDataSetChanged();
        }
    }
}
