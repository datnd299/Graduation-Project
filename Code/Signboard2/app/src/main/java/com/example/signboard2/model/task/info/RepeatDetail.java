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

    public String getFzUnitString(){
        if(getFzUnit().equals("moth")){
            return "Tháng";
        }
        if(getFzUnit().equals("day")){
            return "Ngày";
        }
        if(getFzUnit().equals("week")){
            return "Tuần";
        }
        return "--";
    }
    public  String getFzValsString(){
        if(getFzUnit().equals("moth")){
            String str = "";
            for (int i:getFzVals()) {
                str+=(i+1)+", ";
            }
            if(str.length()>0){
                str.substring(0, str.length() - 2);
            }
            return "Vào các ngày: "+str;
        }
        if(getFzUnit().equals("day")){
            return "Mỗi: "+ 3+" ngày";
        }
        if(getFzUnit().equals("week")){
            String str = "";
            for (int i:getFzVals()) {
                str+="Th "+(i+2)+", ";
            }
            if(str.length()>0){
                str.substring(0, str.length() - 2);
            }
             return "Vào các thứ: "+str;
        }
        return "--";
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
