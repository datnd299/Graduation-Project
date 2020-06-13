package com.example.signboard2.model.account;

import com.example.signboard2.model.Resp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountResponse extends Resp {


    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("data")
    @Expose
    private AccountData data;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AccountData getData() {
        return data;
    }

    public void setData(AccountData data) {
        this.data = data;
    }

}
