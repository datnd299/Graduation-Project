package com.example.signboard2.model.task.info;

import java.util.List;

import com.example.signboard2.model.place.PlaceRental;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeDetail {

    @SerializedName("times")
    @Expose
    private List<Integer> times = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("place_rental")
    @Expose
    private PlaceRental placeRental;
    @SerializedName("amount")
    @Expose
    private Integer amount;

    public List<Integer> getTimes() {
        return times;
    }

    public void setTimes(List<Integer> times) {
        this.times = times;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlaceRental getPlaceRental() {
        return placeRental;
    }

    public void setPlaceRental(PlaceRental placeRental) {
        this.placeRental = placeRental;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}