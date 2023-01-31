/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model;

/**
 *
 * @author nesch
 */
public class Service {
    private int serviceId;
    private String serviceName;
    private float servicePrice;
    private boolean payByPerson;
    private boolean payByDay;

    public Service(String serviceName, float servicePrice, boolean payByPerson, boolean payByDay) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.payByPerson = payByPerson;
        this.payByDay = payByDay;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public float getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(float servicePrice) {
        this.servicePrice = servicePrice;
    }

    public boolean isPayByPerson() {
        return payByPerson;
    }

    public void setPayByPerson(boolean payByPerson) {
        this.payByPerson = payByPerson;
    }

    public boolean isPayByDay() {
        return payByDay;
    }

    public void setPayByDay(boolean payByDay) {
        this.payByDay = payByDay;
    }

   
}
