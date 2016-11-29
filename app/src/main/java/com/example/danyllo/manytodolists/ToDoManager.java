package com.example.danyllo.manytodolists;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Danyllo on 29-11-2016.
 */
public class ToDoManager {
    private static ToDoManager ourInstance = new ToDoManager();
    private static HashMap<String, ArrayList<String>> ManagedList;

    public static ToDoManager getInstance() {
        return ourInstance;
    }

    private ToDoManager() {
        ManagedList = new HashMap<String, ArrayList<String>>();
    }
    public void addCategory(String category) {
        ManagedList.put(category, new ArrayList<String>());
    }
    public void addItem(String category, String item) {
        ManagedList.get(category).add(item);
    }
    public void deleteCategory(String category) {
        ManagedList.remove(category);
    }
    public void deleteItem(String category, String item) {
        ManagedList.get(category).remove(item);
    }
}
