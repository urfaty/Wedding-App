package com.example.weddingapp;

public class Place {

    String  name, city, alcohol;
    int imageId,capacity;
    float rating; // Sonradan

    public Place(int capacity, String name, String city, String alcohol, int imageId, float rating) {
        this.capacity = capacity;
        this.name = name;
        this.city = city;
        this.alcohol = alcohol;
        this.imageId = imageId;
        this.rating = rating;
    }
}