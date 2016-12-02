package com.example.danyllo.manytodolists;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Danyllo on 1-12-2016.
 */

public class ToDoList implements Parcelable{
    private ArrayList<ToDoItem> itemList;
    private String title;

    public ToDoList(String title) {
        this.title = title;
        itemList = new ArrayList<ToDoItem>();
    }

    public ToDoList(Parcel in) {
        this.title = in.readString();
        in.readList(this.itemList, ToDoList.class.getClassLoader());
    }

    public String getString() {
        return this.title;
    }
    public ArrayList<ToDoItem> getItemList() {
        return this.itemList;
    }
    public boolean contains(String item) {
        for (int i = 0; i< itemList.size(); i++) {
            if(itemList.get(i).getTitle().equals(item)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(itemList);
        dest.writeString(title);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ToDoList createFromParcel(Parcel in) {
            return new ToDoList(in);
        }
        public ToDoList[] newArray(int size) {
            return new ToDoList[size];
        }
    };
}
