package com.example.danyllo.manytodolists;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class ListActivity extends AppCompatActivity {
    ListView itemList;
    //ToDoManager toDoManager = ToDoManager.getInstance();
    ToDoManager toDoManager;
    ArrayList<String> itemStrings = new ArrayList<String>();
    ToDoList items;
    CustomAdapter itemAdapter;
    SharedPreferences prefs;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        try {
            try {
                readToDos(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException f) {
            f.printStackTrace();
        }
        setItems();
        setListeners();
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            writeToDos(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setListeners() {
        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //toDoManager.deleteCategory(itemStrings.get(position));
                items.deleteItem(items.getItemList().get(position));
                toDoManager.deleteItem(items.getString(), items.getItemList().get(position));
                reset();
                return false;
            }
        });
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                items.getItemList().get(position).setCompleted(true);
                reset();
            }
        });
    }

    private void setItems() {
        toDoManager = (ToDoManager) getIntent().getSerializableExtra("manager");
        items = (ToDoList) getIntent().getSerializableExtra("items");
        itemStrings = items.getItemStrings();
        itemList = (ListView) findViewById(R.id.listview2);
        itemAdapter = new CustomAdapter(this, items);
        Log.d("GETCOUNT", String.valueOf(itemAdapter.getCount()));
        itemList.setAdapter(itemAdapter);
    }

    private void reset() {
        try {
            writeToDos(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //itemAdapter.update(items);
        itemList.setAdapter(new CustomAdapter(this, items));
        Log.d("GETCOUNT2", String.valueOf(itemAdapter.getCount()));
    }

    public void AddItem(View view) {
        EditText ET = (EditText) findViewById(R.id.editText6);
        String input = ET.getText().toString().trim();
        if (input.length() == 0) { //no empty strings
            Toast toast = Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            if (!items.contains(input)) {
                //toDoManager.addCategory(input);
                ToDoItem newItem = new ToDoItem(input);
                items.addItem(newItem);
                toDoManager.addItem(items.getString(), newItem);
                ET.setText("");
                reset();
            } else { //no entries already in the list
                Toast contains = Toast.makeText(this, "Already added", Toast.LENGTH_SHORT);
                contains.show();
            }
        }
    }

    private void writeToDos(Context context) throws FileNotFoundException, IOException {
        FileOutputStream fileOut = context.openFileOutput("items", Context.MODE_PRIVATE);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(items);
        objOut.close();
    }
    private void readToDos(Context context) throws FileNotFoundException, ClassNotFoundException, IOException {
        FileInputStream fileIn = context.openFileInput("items");
        ObjectInputStream objIn = new ObjectInputStream(fileIn);
        items = (ToDoList) objIn.readObject();
        objIn.close();
    }
}
