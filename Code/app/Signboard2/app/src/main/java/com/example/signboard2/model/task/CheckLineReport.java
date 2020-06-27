package com.example.signboard2.model.task;

import com.example.signboard2.model.place.PlaceRental;

import java.util.List;

public class CheckLineReport {
    private PlaceRental place;
    private List<ImageReport> imgLst;

    public PlaceRental getPlace() {
        return place;
    }

    public void setPlace(PlaceRental place) {
        this.place = place;
    }

    public List<ImageReport> getImgLst() {
        return imgLst;
    }

    public void setImgLst(List<ImageReport> imgLst) {
        this.imgLst = imgLst;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getCmt() {
        return cmt;
    }

    public void setCmt(int cmt) {
        this.cmt = cmt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int rating;
    private int cmt;
    private int status;
}
