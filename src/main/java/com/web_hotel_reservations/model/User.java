/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model;

/**
 *
 * @author THALIA
 */
public class User {
    private int userId;
    private String password;
    private String email;
    private int role;

    public User(String password, String email, int role) {
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public int getId() {
        return userId;
    }

    public void setId(int user_id) {
        this.userId = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }    
    
}
