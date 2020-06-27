package com.example.signboard2.model.task;

import android.graphics.Bitmap;

public class ImageReport {
    private Bitmap img;
    private Boolean uploading;
    private Boolean takingPhoto;

    public Bitmap getImg() {
        return img;
    }

    public Boolean getTakingPhoto() {
        return takingPhoto;
    }

    public void setTakingPhoto(Boolean takingPhoto) {
        this.takingPhoto = takingPhoto;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Boolean getUploading() {
        return uploading;
    }

    public void setUploading(Boolean uploading) {
        this.uploading = uploading;
    }

    public Boolean getHaveImg() {
        return haveImg;
    }

    public void setHaveImg(Boolean haveImg) {
        this.haveImg = haveImg;
    }

    private Boolean haveImg;

    public ImageReport(){
        this.img = null;
        this.haveImg = false;
        this.uploading = false;
        this.takingPhoto = false;
    }
}
