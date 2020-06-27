package com.example.signboard2.model.task;


import java.util.List;

import com.example.signboard2.model.account.Account;
import com.example.signboard2.model.place.PlaceRental;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckTask {

    @SerializedName("accs")
    @Expose
    private List<Account> accs = null;
    @SerializedName("place_rental")
    @Expose
    private List<PlaceRental> placeRental = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("_id")
    @Expose
    private String id;

    public List<Account> getAccs() {
        return accs;
    }

    public void setAccs(List<Account> accs) {
        this.accs = accs;
    }

    public List<PlaceRental> getPlaceRental() {
        return placeRental;
    }

    public void setPlaceRental(List<PlaceRental> placeRental) {
        this.placeRental = placeRental;
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

}
