/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model;

/**
 *
 * @author THALIA
 */
public class Reservation {
    
    public int reservationId;
    public int customerId;
    public String checkIn;
    public String checkOut;
    public int state;

    public Reservation(int customerId, String checkIn, String checkOut, int state) {
        this.customerId = customerId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.state = state;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    
}
