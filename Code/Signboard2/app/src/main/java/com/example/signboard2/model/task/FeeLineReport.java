package com.example.signboard2.model.task;

import com.example.signboard2.model.place.PlaceRental;
import com.example.signboard2.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class FeeLineReport {
    private PlaceRental place;
    private double amount;
    private JsonArray times;
    private String timeUnit;
    private String role;
    private boolean emConfirm;
    private boolean ptBConfirm;
    private String emNote;
    private String ptBNote;

    public boolean isEmConfirm() {
        return emConfirm;
    }

    public void setEmConfirm(boolean emConfirm) {
        this.emConfirm = emConfirm;
    }

    public boolean isPtBConfirm() {
        return ptBConfirm;
    }

    public void setPtBConfirm(boolean ptBConfirm) {
        this.ptBConfirm = ptBConfirm;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public boolean isRoleA(){
        if(role.equals("A")){
            return true;
        }
        return false;
    }


    public String getTimeString(){
        String str = Utils.toTimeUnitString(place.getTimeUnit())+" thứ: ";
        for (JsonElement jET :times){
            str+=jET.getAsString()+", ";
        }
        if (times.size()>0){
            str = str.substring(0,str.length()-2);
        }
        return str;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getEmNote() {
        return emNote;
    }

    public void setEmNote(String emNote) {
        this.emNote = emNote;
    }

    public String getPtBNote() {
        return ptBNote;
    }

    public void setPtBNote(String ptBNote) {
        this.ptBNote = ptBNote;
    }

    public PlaceRental getPlace() {
        return place;
    }

    public void setPlace(PlaceRental place) {
        this.place = place;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public JsonArray getTimes() {
        return times;
    }

    public void setTimes(JsonArray times) {
        this.times = times;
    }

}
