package com.example.admin.mobile_middle_out;

/**
 * Created by Admin on 08/11/2015.
 */
public class Item {

    private String title;
    private String description;

    public Item(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }

    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
}