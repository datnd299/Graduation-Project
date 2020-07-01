package com.example.signboard2.model.task.report;

import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.ImageSignboardReport;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReportTaskReportObj {

    public ReportTaskReportObj(String id, List<ImageSignboardReport> imageSignboardReportLst){
        this.id = id;
        Report2 rp = new Report2();
        this.report = rp;
        List<Signboard3> signboardLst =new ArrayList<>();
        rp.setSignboards(signboardLst);
        for (ImageSignboardReport sbRp:imageSignboardReportLst){
            Signboard3 sb = new Signboard3();
            sb.setSId(sbRp.getId());
            sb.setNote(sbRp.getNote());
            signboardLst.add(sb);
            sb.setRating(sbRp.getRating());
            List<String> imgs = new ArrayList<>();
            sb.setImgs(imgs);
            for (ImageReport imgReport:sbRp.getImgs()){
                if(imgReport.getUploadedID()!=null){
                    imgs.add(imgReport.getUploadedID());
                }
            }
        }
    }
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("report")
    @Expose
    private Report2 report;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Report2 getReport() {
        return report;
    }

    public void setReport(Report2 report) {
        this.report = report;
    }

}


 class Report2 {

    @SerializedName("signboards")
    @Expose
    private List<Signboard3> signboards = null;

    public List<Signboard3> getSignboards() {
        return signboards;
    }

    public void setSignboards(List<Signboard3> signboards) {
        this.signboards = signboards;
    }

}


 class Signboard3 {

    @SerializedName("s_id")
    @Expose
    private String sId;
    @SerializedName("imgs")
    @Expose
    private List<String> imgs = null;
    @SerializedName("rating")
    @Expose
    private Float rating;
    @SerializedName("note")
    @Expose
    private String note;

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}