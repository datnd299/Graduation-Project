package com.example.signboard2.model;

import com.example.signboard2.model.task.Task;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespJObj extends Resp {
    @SerializedName("data")
    @Expose
    private JsonObject data = null;
    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }


}
