package com.example.danyllo.manytodolists;

/**
 * Created by Danyllo on 2-12-2016.
 */

//Courtesy of: http://www.vogella.com/tutorials/AndroidListView/article.html

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class CustomAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;
    private final boolean[] completionValues;

    public CustomAdapter(Context context, ToDoList values) {
        super(context, R.layout.activity_list, values.getItemStrings());
        this.context = context;
        this.values = values.getItemStrings();
        this.completionValues = values.getCompletionValues();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View rowView = inflater.inflate(R.layout.activity_list, parent, false);
        View rowView = inflater.inflate(simple_list_item_1, parent, false);
        ListView listView = (ListView) rowView.findViewById(R.id.listview2);
        if (completionValues[position]) {
            listView.getChildAt(position).setBackgroundColor(Color.YELLOW);
        }
        return rowView;
    }
}
