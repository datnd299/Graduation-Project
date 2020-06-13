package com.example.signboard2.model.task;

import com.example.signboard2.model.Resp;

import java.util.List;

import com.example.signboard2.model.account.Account;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskLst extends Resp {

    @SerializedName("data")
    @Expose
    private List<Task> data = null;
    @SerializedName("acc")
    @Expose
    private Account acc;



    public List<Task> getData() {
        return data;
    }

    public void setData(List<Task> data) {
        this.data = data;
    }

    public Account getAcc() {
        return acc;
    }

    public void setAcc(Account acc) {
        this.acc = acc;
    }

}
