package com.example.signboard2.model.task;

import com.example.signboard2.model.place.PlaceRental;

public class FeeLineReport {
    private PlaceRental place;
    private double amount;
    private int[] times;
    private int emStatus;
    private int ptBStatus;

    public PlaceRental getPlace() {
        return place;
    }

    public void setPlace(PlaceRental place) {
        this.place = place;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int[] getTimes() {
        return times;
    }

    public void setTimes(int[] times) {
        this.times = times;
    }

    public int getEmStatus() {
        return emStatus;
    }

    public void setEmStatus(int emStatus) {
        this.emStatus = emStatus;
    }

    public int getPtBStatus() {
        return ptBStatus;
    }

    public void setPtBStatus(int ptBStatus) {
        this.ptBStatus = ptBStatus;
    }
}
