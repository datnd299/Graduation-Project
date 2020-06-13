package com.example.signboard2.model.task;


import java.util.List;

import com.example.signboard2.model.account.Account;
import com.example.signboard2.model.place.PlaceRental;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetupTask {

    @SerializedName("accs")
    @Expose
    private List<Account> accs = null;
    @SerializedName("signboards")
    @Expose
    private List<String> signboards = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("place_rental")
    @Expose
    private PlaceRental placeRental;

    public List<Account> getAccs() {
        return accs;
    }

    public void setAccs(List<Account> accs) {
        this.accs = accs;
    }

    public List<String> getSignboards() {
        return signboards;
    }

    public void setSignboards(List<String> signboards) {
        this.signboards = signboards;
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

}
