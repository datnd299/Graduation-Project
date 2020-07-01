package com.example.signboard2.model.task.report;
import java.util.ArrayList;
import java.util.List;

import com.example.signboard2.model.task.CheckLineReport;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.ImageSignboardReport;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;







public class CheckTaskReportObj {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("place_rental")
    @Expose
    private List<PlaceRental> placeRental = null;

    public CheckTaskReportObj(String id,List<CheckLineReport> cLst, String note) {
        this.id = id;
        this.note = note;
        placeRental = new ArrayList<>();
        for (CheckLineReport line:cLst){
            PlaceRental pl = new PlaceRental();
            pl.setPlId(line.getPlace().getId());

            List<Signboard2> sbLst = new ArrayList<>();
            pl.setSignboards(sbLst);
            placeRental.add(pl);
            for (ImageSignboardReport imageSignboardReport : line.getImgsSignboardReportList()){
                Signboard2 sb = new Signboard2();
                sb.setSId(imageSignboardReport.getId());
                sb.setRating(imageSignboardReport.getRating());
                sb.setNote(imageSignboardReport.getNote());
                sbLst.add(sb);
                List<String> imgLst = new ArrayList<>();
                sb.setImgs(imgLst);
                for (ImageReport img :imageSignboardReport.getImgs()){
                    if(img.getUploadedID()!=null){
                        imgLst.add(img.getUploadedID());
                    }
                }

            }
        }
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<PlaceRental> getPlaceRental() {
        return placeRental;
    }

    public void setPlaceRental(List<PlaceRental> placeRental) {
        this.placeRental = placeRental;
    }

}


 class PlaceRental {

    @SerializedName("pl_id")
    @Expose
    private String plId;
    @SerializedName("signboards")
    @Expose
    private List<Signboard2> signboards = null;

    public String getPlId() {
        return plId;
    }

    public void setPlId(String plId) {
        this.plId = plId;
    }

    public List<Signboard2> getSignboards() {
        return signboards;
    }

    public void setSignboards(List<Signboard2> signboards) {
        this.signboards = signboards;
    }

}


class Signboard2 {

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
