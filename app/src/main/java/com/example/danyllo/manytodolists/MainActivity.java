package com.example.danyllo.manytodolists;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity {
    ListView categoryList;
    ToDoManager toDoManager = ToDoManager.getInstance();
    ArrayList<String> categories = new ArrayList<String>();
    ArrayAdapter<String> categoryAdapter;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        try {
            try {
                readToDos(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException f) {
            f.printStackTrace();
        }
        setCategories();
        setListeners();
    }

    /*private void readList() {
        toDoManager.readList();
    }*/

    /*private ArrayList<String> readCategories() {
        ArrayList<String> listToReadTo = new ArrayList<String>();
        for (int i = 0; i < ManagedList.size(); i++) {
            listToReadTo.add(ManagedList.get(i).getString());
        }
        return listToReadTo;
    }*/

    private void setCategories() {
        //ManagedList = toDoManager.readList();
        categoryList = (ListView) findViewById(R.id.listView);
        categories = toDoManager.readCategories();
        categoryAdapter = new ArrayAdapter<String>(this, simple_list_item_1, categories);
        categoryList.setAdapter(categoryAdapter);
    }

    private void reset() {
        //ManagedList = toDoManager.readList();
        categories = toDoManager.readCategories();
        categoryAdapter.clear();
        categoryAdapter.addAll(categories);
        categoryAdapter.notifyDataSetChanged();
        try {
            writeToDos(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setListeners() {
        categoryList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toDoManager.deleteCategory(categories.get(position));
                reset();
                return false;
            }
        });
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                goToCategoryItems(toDoManager.getCategory(categories.get(position)));

            }
        });
    }

    private void goToCategoryItems(ToDoList category) {
        Intent listActivity = new Intent(this, ListActivity.class);
        //listActivity.putExtra("items", category);
        listActivity.putExtra("items", category.getString());
        startActivity(listActivity);
    }


    public void AddCategory(View view) {
        EditText ET = (EditText) findViewById(R.id.editText5);
        String input = ET.getText().toString().trim();
        if (input.length() == 0) { //no empty strings
            Toast toast = Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            if (!categories.contains(input)) {
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
