package com.example.signboard2.model.task.info;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepeatDetail {

    @SerializedName("fz_vals")
    @Expose
    private List<Integer> fzVals = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("fz_unit")
    @Expose
    private String fzUnit;
    @SerializedName("remind_before")
    @Expose
    private Integer remindBefore;
    @SerializedName("random")
    @Expose
    private Boolean random;
    @SerializedName("time_to_complete")
    @Expose
    private Integer timeToComplete;

    public List<Integer> getFzVals() {
        return fzVals;
    }

    public void setFzVals(List<Integer> fzVals) {
        this.fzVals = fzVals;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFzUnit() {
        return fzUnit;
    }

    public void setFzUnit(String fzUnit) {
        this.fzUnit = fzUnit;
    }

    public Integer getRemindBefore() {
        return remindBefore;
    }

    public void setRemindBefore(Integer remindBefore) {
        this.remindBefore = remindBefore;
    }

    public Boolean getRandom() {
        return random;
    }

    public void setRandom(Boolean random) {
        this.random = random;
    }

    public Integer getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(Integer timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

}
