package com.example.signboard2.model.task;

import com.example.signboard2.ui.tasks.components.ListImageAdapter;

import java.util.List;

public class ImageSignboardReport {
    private String name;
    private String id;
    private float rating;
    private String note;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private List<ImageReport> imgs;

    private ListImageAdapter listImageAdapter;

    public ListImageAdapter getListImageAdapter() {
        return listImageAdapter;
    }

    public void setListImageAdapter(ListImageAdapter listImageAdapter) {
        this.listImageAdapter = listImageAdapter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ImageReport> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImageReport> imgs) {
        this.imgs = imgs;
    }
}
