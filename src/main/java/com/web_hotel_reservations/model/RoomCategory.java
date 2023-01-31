/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model;

/**
 *
 * @author THALIA
 */
public class RoomCategory {

    private int categoryId;
    private String categoryName;
    private String categoryDescription;
    private int capacity;
    private int beds;
    private float price;
    
    public RoomCategory(String categoryName, String categoryDescription, int capacity, int beds, float price) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.capacity = capacity;
        this.beds = beds;
        this.price = price;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
 
    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
}
