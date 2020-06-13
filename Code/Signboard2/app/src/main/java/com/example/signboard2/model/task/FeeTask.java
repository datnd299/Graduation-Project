package com.example.signboard2.model.task;

import java.util.List;

import com.example.signboard2.model.account.Account;
import com.example.signboard2.model.task.info.FeeDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeTask {

    @SerializedName("accs")
    @Expose
    private List<Account> accs = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("feeDetail")
    @Expose
    private List<FeeDetail> feeDetail = null;

    public List<Account> getAccs() {
        return accs;
    }

    public void setAccs(List<Account> accs) {
        this.accs = accs;
    }

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

    public List<FeeDetail> getFeeDetail() {
        return feeDetail;
    }

    public void setFeeDetail(List<FeeDetail> feeDetail) {
        this.feeDetail = feeDetail;
    }

}
