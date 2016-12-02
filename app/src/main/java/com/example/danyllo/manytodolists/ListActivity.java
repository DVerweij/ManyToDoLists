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
    ToDoManager toDoManager = ToDoManager.getInstance();
    ArrayList<String> itemStrings = new ArrayList<String>();
    ToDoList items;
    ArrayAdapter<String> itemAdapter;
    SharedPreferences prefs;

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

    private void setListeners() {
        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toDoManager.deleteCategory(itemStrings.get(position));
                reset();
                return false;
            }
        });
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("BOI", "BOI");
            }
        });
    }

    private void setItems() {
        //items = getIntent().getExtras().getParcelable("items");
        String category = getIntent().getStringExtra("items");
        //ManagedList = toDoManager.readList();
        items = toDoManager.getCategory(category);
        itemStrings = items.getItemStrings();
        itemList = (ListView) findViewById(R.id.listview2);
        //itemAdapter = new ArrayAdapter<String>(this, simple_list_item_1, itemStrings);
        itemAdapter = new CustomAdapter(this, items);
        itemList.setAdapter(itemAdapter);
    }

   /* private ArrayList<String> readItems() {
        ArrayList<ToDoItem> itemList = items.getItemList();
        ArrayList<String> listToReadTo = new ArrayList<String>();
        for (int i = 0; i < itemList.size(); i++) {
            listToReadTo.add(itemList.get(i).getTitle());
        }
        return listToReadTo;
    }*/

    private void reset() {
        //ManagedList = toDoManager.readList();
        itemStrings = items.getItemStrings();
        itemAdapter.clear();
        itemAdapter.addAll(itemStrings);
        itemAdapter.notifyDataSetChanged();
        try {
            writeToDos(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                reset();
            } else { //no entries already in the list
                Toast contains = Toast.makeText(this, "Already added", Toast.LENGTH_SHORT);
                contains.show();
            }
        }
    }

    private void writeToDos(Context context) throws FileNotFoundException, IOException {
        FileOutputStream fileOut = context.openFileOutput("todos", Context.MODE_PRIVATE);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(toDoManager.readList());
        objOut.close();
    }
    private void readToDos(Context context) throws FileNotFoundException, ClassNotFoundException, IOException {
        FileInputStream fileIn = context.openFileInput("todos");
        ObjectInputStream objIn = new ObjectInputStream(fileIn);
        toDoManager.setManagedList((ArrayList<ToDoList>) objIn.readObject());
        objIn.close();
    }
}
