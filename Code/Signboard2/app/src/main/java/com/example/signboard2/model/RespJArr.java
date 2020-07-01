package com.example.signboard2.model;

import com.example.signboard2.model.task.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.List;

public class RespJArr extends Resp {
    @SerializedName("data")
    @Expose
    private JsonArray data = null;
    public JsonArray getData() {
        return data;
    }

    public void setData(JsonArray data) {
        this.data = data;
    }


}
