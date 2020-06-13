package com.example.signboard2.model.task;

import com.example.signboard2.model.place.PlaceRental;
import com.example.signboard2.model.task.info.RepeatDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportTask {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("place_rental")
    @Expose
    private PlaceRental placeRental;
    @SerializedName("root_task")
    @Expose
    private Object rootTask;
    @SerializedName("repeat_type")
    @Expose
    private String repeatType;
    @SerializedName("repeat_detail")
    @Expose
    private RepeatDetail repeatDetail;

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

    public Object getRootTask() {
        return rootTask;
    }

    public void setRootTask(Object rootTask) {
        this.rootTask = rootTask;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public RepeatDetail getRepeatDetail() {
        return repeatDetail;
    }

    public void setRepeatDetail(RepeatDetail repeatDetail) {
        this.repeatDetail = repeatDetail;
    }

}
