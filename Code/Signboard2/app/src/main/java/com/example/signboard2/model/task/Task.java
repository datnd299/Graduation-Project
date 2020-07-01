package com.example.signboard2.model.task;
import android.os.Build;

import com.example.signboard2.model.account.Account;
import com.example.signboard2.model.place.PlaceRental;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.RequiresApi;

public class Task {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("acc_created")
    @Expose
    private Account accCreated;
    @SerializedName("pt_a")
    @Expose
    private String ptA;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("start")
    @Expose
    private Date start;
    @SerializedName("end")
    @Expose
    private Date end;
    @SerializedName("setup_task")
    @Expose
    private SetupTask setupTask;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("check_task")
    @Expose
    private CheckTask checkTask;
    @SerializedName("fee_task")
    @Expose
    private FeeTask feeTask;
    @SerializedName("report_task")
    @Expose
    private ReportTask reportTask;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Account getAccCreated() {
        return accCreated;
    }

    public void setAccCreated(Account accCreated) {
        this.accCreated = accCreated;
    }

    public String getPtA() {
        return ptA;
    }

    public void setPtA(String ptA) {
        this.ptA = ptA;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public SetupTask getSetupTask() {
        return setupTask;
    }

    public void setSetupTask(SetupTask setupTask) {
        this.setupTask = setupTask;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public CheckTask getCheckTask() {
        return checkTask;
    }

    public void setCheckTask(CheckTask checkTask) {
        this.checkTask = checkTask;
    }

    public FeeTask getFeeTask() {
        return feeTask;
    }

    public void setFeeTask(FeeTask feeTask) {
        this.feeTask = feeTask;
    }

    public ReportTask getReportTask() {
        return reportTask;
    }

    public void setReportTask(ReportTask reportTask) {
        this.reportTask = reportTask;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<PlaceRental> getPlacesOfTask(){
        if(setupTask!=null){
            List l =new ArrayList<PlaceRental>();
            l.add(setupTask.getPlaceRental());
            return l;
        }else if(feeTask!=null){
            List l = new ArrayList<PlaceRental>();
            feeTask.getFeeDetail().forEach(feeDetail -> {
                l.add(feeDetail.getPlaceRental());
            });
        }else if(reportTask!=null){
            List l =new ArrayList<PlaceRental>();
            l.add(reportTask.getPlaceRental());
            return l;
        }else {
            return checkTask.getPlaceRental();
        }
        return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getPlacesString(){
        String  str = "";
        List<PlaceRental> l = getPlacesOfTask();
        if(l==null){
            return "";
        }
        for (int i =0;i<l.size();i++){
            str+=(l.get(i).getName()+", ");
        }

        if(l.size()>1){
            str= str.substring(0, str.length() - 2);
        }
        return  str;
    }

    public String getStartTimesString(){

        Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String s = formatter.format(getStart());
        return s;
    }
    public String getEndTimesString(){

        Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String s = formatter.format(getEnd());
        return s;
    }

}
