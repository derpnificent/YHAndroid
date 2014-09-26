package com.example.admin.assignment2b.Models;

/**
 * Created by Admin on 2014-09-23.
 */


public class GalleryFrame {

    //initializes variables
    private String url;
    private String name;
    private String age;
    private String description;

    //Set the variables
    public GalleryFrame(String url, String name, String age, String description){
        this.url = url;
        this.name = name;
        this.age = age;
        this.description = description;

    }

    //Get the url and returns it
    public String getUrl() {
        return url;
    }

    //Get the Name and returns it
    public String getName() {
        return name;
    }

    //Get the Age and return it
    public String getAge() {
        return age;
    }

    //Get the description and return it
    public String getDescription() {
        return description;
    }

}
