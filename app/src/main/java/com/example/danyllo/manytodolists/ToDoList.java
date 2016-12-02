package com.example.danyllo.manytodolists;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Danyllo on 1-12-2016.
 */

public class ToDoList implements Serializable{
    private ArrayList<ToDoItem> itemList;
    private String title;

    public ToDoList(String title) {
        this.title = title;
        itemList = new ArrayList<ToDoItem>();
    }
    public void addItem(ToDoItem item) {
        itemList.add(item);
    }

    public void deleteItem(ToDoItem item) {
        itemList.remove(item);
    }
    public String getString() {
        return this.title;
    }
    public ArrayList<ToDoItem> getItemList() {
        return this.itemList;
    }
    public ArrayList<String> getItemStrings() {
        ArrayList<String> itemStrings = new ArrayList<String>();
        for (int i = 0; i < itemList.size(); i++) {
            itemStrings.add(itemList.get(i).getTitle());
        }
        return itemStrings;
    }

    public boolean[] getCompletionValues() {
        Log.d("TAG", String.valueOf(itemList.size()));
        boolean[] bools = new boolean[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            bools[i] = itemList.get(i).getCompleted();
        }
        return bools;
    }

    public boolean contains(String item) {
        for (int i = 0; i< itemList.size(); i++) {
            if(itemList.get(i).getTitle().equals(item)) {
                return true;
            }
        }
        return false;
    }
}
