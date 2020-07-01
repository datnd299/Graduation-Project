package com.example.signboard2.model.task;

import com.example.signboard2.model.place.PlaceRental;

import java.util.List;

public class CheckLineReport {
    private PlaceRental place;
    private List<ImageSignboardReport> imgsSignboardReportList;

    public PlaceRental getPlace() {
        return place;
    }

    public void setPlace(PlaceRental place) {
        this.place = place;
    }

    public List<ImageSignboardReport> getImgsSignboardReportList() {
        return imgsSignboardReportList;
    }

    public void setImgsSignboardReportList(List<ImageSignboardReport> imgsSignboardReportList) {
        this.imgsSignboardReportList = imgsSignboardReportList;
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
