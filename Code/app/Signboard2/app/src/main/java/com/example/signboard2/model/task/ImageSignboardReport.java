package com.example.signboard2.model.task;

import java.util.List;

public class ImageSignboardReport {
    private String name;
    private String id;
    private List<ImageReport> imgs;

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
