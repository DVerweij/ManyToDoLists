package com.example.danyllo.manytodolists;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Danyllo on 29-11-2016.
 */

public class ToDoItem implements Parcelable{
    private String title;
    private boolean completed;

    public ToDoItem(String title) {
        this.title = title;
        this.completed = false;
    }

    public ToDoItem(Parcel in) {
        this.title = in.readString();
        this.completed = Boolean.getBoolean(in.readString());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(String.valueOf(this.completed));
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ToDoItem createFromParcel(Parcel in) {
            return new ToDoItem(in);
        }

        public ToDoItem[] newArray(int size) {
            return new ToDoItem[size];
        }
    };
}
