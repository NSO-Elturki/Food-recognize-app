package com.example.bigprojectwhitelist;
import android.graphics.drawable.Drawable;


public class ListInfo {

    private String name;
    Drawable icon;

    public ListInfo(String name, Drawable icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }
}
