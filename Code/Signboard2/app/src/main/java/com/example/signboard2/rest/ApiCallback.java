package com.example.signboard2.rest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.signboard2.model.Resp;
import com.example.signboard2.ui.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;

public abstract class ApiCallback<T extends Resp> implements Callback<T> {


    Context context;
    public ApiCallback(Context context) {
            this.context = context;
    }

    public abstract  void handleSuccess(Call<T> call, retrofit2.Response<T> response);
    public abstract  void handleFailure(Call<T> call, retrofit2.Response<T> response,Throwable t);

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
        handleFailure(call,null,t);
    }

    @Override
    public void onResponse(Call<T> call, retrofit2.Response<T> response) {
        if (response.body().getStatus().equals("fail")) {
            // [..do something with error]
            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
            handleFailure(call,response,new Throwable(response.body().getMessage()));
        }
        else {
            handleSuccess(call, response);
        }
    }
}
