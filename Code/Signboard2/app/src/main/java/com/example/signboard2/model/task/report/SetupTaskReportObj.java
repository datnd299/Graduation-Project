package com.example.signboard2.model.task.report;

import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.ImageSignboardReport;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class SetupTaskReportObj {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("report")
    @Expose
    private Report report;

    public SetupTaskReportObj(String id, Double newLat, Double newLng, String note, List<ImageSignboardReport> imagesSignboardReportList) {
        this.id = id;
        this.report = new Report();
        NewLatLng newLatLng = new NewLatLng();
        newLatLng.setLat(newLat);
        report.setNote(note);
        newLatLng.setLng(newLng);
        report.setNewLatLng(newLatLng);

        List<Signboard> sbLst = new ArrayList<>();
        for (ImageSignboardReport imageSignboardReport:imagesSignboardReportList){
            Signboard sb = new Signboard();
            sb.setSId(imageSignboardReport.getId());
            List<String> imgs = new ArrayList<>();
            for (ImageReport imageReport:imageSignboardReport.getImgs()){
                if(imageReport.getUploadedID()!=null){
                    imgs.add(imageReport.getUploadedID());
                }

            }
            sb.setImgs(imgs);
            sbLst.add(sb);
        }
        this.report.setSignboards(sbLst);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

}



class NewLatLng {

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}


class Report {

    @SerializedName("new_lat_lng")
    @Expose
    private NewLatLng newLatLng;
    @SerializedName("signboards")
    @Expose
    private List<Signboard> signboards = null;
    @SerializedName("note")
    @Expose
    private String note;

    public NewLatLng getNewLatLng() {
        return newLatLng;
    }

    public void setNewLatLng(NewLatLng newLatLng) {
        this.newLatLng = newLatLng;
    }

    public List<Signboard> getSignboards() {
        return signboards;
    }

    public void setSignboards(List<Signboard> signboards) {
        this.signboards = signboards;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}


 class Signboard {

    @SerializedName("s_id")
    @Expose
    private String sId;
    @SerializedName("imgs")
    @Expose
    private List<String> imgs = null;

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

}
