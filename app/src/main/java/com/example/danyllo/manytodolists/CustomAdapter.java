package com.example.danyllo.manytodolists;

/**
 * Created by Danyllo on 2-12-2016.
 */

//Courtesy of: http://www.vogella.com/tutorials/AndroidListView/article.html

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
    private ArrayList<String> values;
    private boolean[] completionValues;

    public CustomAdapter(Context context, ToDoList values) {
        super(context, R.layout.activity_list, values.getItemStrings());
        this.context = context;
        this.values = values.getItemStrings();
        this.completionValues = values.getCompletionValues();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.d("FINAL SHINE", "FINAL FLASH");
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View rowView = inflater.inflate(R.layout.activity_list, parent, false);
        View rowView = inflater.inflate(R.layout.listview_row_item, parent, false);
        TextView listRow = (TextView) rowView.findViewById(R.id.rowItem);
        /*if (completionValues[position]) {
            listRow.setBackgroundColor(Color.YELLOW);
        }*/
        listRow.setText(values.get(position));
        return rowView;
    }

    public void update(ToDoList newValues) {
        this.values = newValues.getItemStrings();
        this.completionValues = newValues.getCompletionValues();
        notifyDataSetChanged();
        Log.d("CHANGED", "CHANGED");
    }
}
