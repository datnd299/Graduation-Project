package com.example.signboard2.model.task.report;
import java.util.ArrayList;
import java.util.List;

import com.example.signboard2.model.task.FeeLineReport;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FeeTaskReportObj {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("report")
    @Expose
    private List<Report3> report = null;

    public FeeTaskReportObj(String id, List<FeeLineReport> fLst) {
        this.id = id;
        List<Report3> rpLst = new ArrayList<>();

        this.report = rpLst;
        for (FeeLineReport flr: fLst) {
            Report3 rp = new Report3();
            rp.setPlId(flr.getPlace().getId());
            if(flr.isRoleA()){
                rp.setConfirm(flr.isEmConfirm());
                rp.setNote(flr.getEmNote());
            }else {
                rp.setConfirm(flr.isPtBConfirm());
                rp.setNote(flr.getPtBNote());
            }
            rpLst.add(rp);
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Report3> getReport() {
        return report;
    }

    public void setReport(List<Report3> report) {
        this.report = report;
    }

}


class Report3 {

    @SerializedName("pl_id")
    @Expose
    private String plId;
    @SerializedName("confirm")
    @Expose
    private Boolean confirm;
    @SerializedName("note")
    @Expose
    private String note;

    public String getPlId() {
        return plId;
    }

    public void setPlId(String plId) {
        this.plId = plId;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
