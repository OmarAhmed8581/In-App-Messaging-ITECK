package com.itecknologi.iteckapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import java.util.List;

public class SecondLevelAdapter extends BaseExpandableListAdapter {

    private Context context;
    List<String[]> data;
    String[] headers;

    public SecondLevelAdapter(Context context, String[] headers, List<String[]> data) {
        this.context = context;
        this.data = data;
        this.headers = headers;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return headers[groupPosition];
    }

    @Override
    public int getGroupCount() {

        return headers.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_row2, null);
        TextView text = (TextView) convertView.findViewById(R.id.listTitle);
        String groupText = getGroup(groupPosition).toString();
        text.setText(groupText);

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        String[] childData;

        childData = data.get(groupPosition);
        return childData[childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_row3, null);
        TextView textView = (TextView) convertView.findViewById(R.id.listTitle);

        String[] childArray = data.get(groupPosition);
        String text = childArray[childPosition];

        textView.setText(text);
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String[] childData = data.get(groupPosition);
//                Toast.makeText(this,childData[childPosition].toString(),Toast.LENGTH_SHORT).show();
//            }
//        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String[] children = data.get(groupPosition);
        return children.length;
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
