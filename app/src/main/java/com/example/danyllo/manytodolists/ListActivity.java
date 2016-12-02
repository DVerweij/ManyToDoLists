package com.example.danyllo.manytodolists;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class ListActivity extends AppCompatActivity {
    ListView itemList;
    ToDoManager toDoManager = ToDoManager.getInstance();
    ArrayList<String> itemStrings = new ArrayList<String>();
    //ArrayList<ToDoList> ManagedList;
    ToDoList items;
    ArrayAdapter<String> itemAdapter;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setItems();
    }

    private void setItems() {
        items = getIntent().getExtras().getParcelable("items");
        itemStrings = readItems();
        itemList = (ListView) findViewById(R.id.listview2);
        itemAdapter = new ArrayAdapter<String>(this, simple_list_item_1, itemStrings);
        itemList.setAdapter(itemAdapter);
    }

    private ArrayList<String> readItems() {
        ArrayList<ToDoItem> itemList = items.getItemList();
        ArrayList<String> listToReadTo = new ArrayList<String>();
        for (int i = 0; i < itemList.size(); i++) {
            listToReadTo.add(itemList.get(i).getTitle());
        }
        return listToReadTo;
    }

    public void AddItem(View view) {
        EditText ET = (EditText) findViewById(R.id.editText6);
        String input = ET.getText().toString().trim();
        if (input.length() == 0) { //no empty strings
            Toast toast = Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            if (!items.contains(input)) {
                toDoManager.addCategory(input);
                ET.setText("");
                //reset();
            } else { //no entries already in the list
                Toast contains = Toast.makeText(this, "Already added", Toast.LENGTH_SHORT);
                contains.show();
            }
        }
    }
}
