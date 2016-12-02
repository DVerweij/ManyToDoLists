package com.example.danyllo.manytodolists;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Danyllo on 29-11-2016.
 */

public class ToDoItem implements Serializable{
    private String title;
    private boolean completed;

    public ToDoItem(String title) {
        this.title = title;
        this.completed = false;
    }
    public void setString(String item) {
        this.title = item;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public String getTitle() {
        return this.title;
    }
    public boolean getCompleted() {
        return this.completed;
    }
}
