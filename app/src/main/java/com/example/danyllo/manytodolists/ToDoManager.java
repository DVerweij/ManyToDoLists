package com.example.danyllo.manytodolists;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Danyllo on 29-11-2016.
 */
public class ToDoManager implements Serializable{
    private static ToDoManager ourInstance = new ToDoManager();
    private static ArrayList<ToDoList> ManagedList;

    public static ToDoManager getInstance() {
        return ourInstance;
    }

    private ToDoManager() {
        ManagedList = new ArrayList<ToDoList>();
    }
    /*public void addCategory(String category) {
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
    public ArrayList<String> getCategory(String category) {
        return ManagedList.get(category);
    }*/

    public ArrayList<ToDoList> readList() {
        return ManagedList;
    }

    public ArrayList<String> readCategories() {
        ArrayList<String> categories = new ArrayList<String>();
        for (int i = 0; i < ManagedList.size(); i++) {
            categories.add(ManagedList.get(i).getString());
        }
        return categories;
    }

    public void setManagedList(ArrayList<ToDoList> list) {
        ManagedList = list;
    }

    public ToDoList getCategory(String category) {
        for (int i = 0; i < ManagedList.size(); i++) {
            if (ManagedList.get(i).getString().equals(category)) {
                return ManagedList.get(i);
            }
        }
        return null;
    }
    public void deleteCategory(String category) {
        ManagedList.remove(getCategory(category));
    }

    public void addCategory(String category) {
        ManagedList.add(new ToDoList(category));
    }

    public void addItem(String category, ToDoItem item) {
        getCategory(category).addItem(item);
    }
    public void deleteItem(String category, ToDoItem item) {
        getCategory(category).deleteItem(item);
    }
}
