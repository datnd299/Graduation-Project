package com.example.signboard2.rest;

import com.example.signboard2.model.RespJArr;
import com.example.signboard2.model.RespJObj;
import com.example.signboard2.model.account.AccountResponse;
import com.example.signboard2.model.task.TaskLst;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by fariz ramadhan.
 * website : www.farizdotid.com
 * github : https://github.com/farizdotid
 * linkedin : https://www.linkedin.com/in/farizramadhan/
 */
public interface BaseAPIService {

    /*
    Fungsi @Path disini adalah untuk mengisi value yang sudah kita set.
    Contoh : {username} disini nantinya akan diisi dengan kebutuhan yang disesuaikan.
    Observable disini ialah dari RxJava. Karena pada contoh disini kita akan menggabungkan
    Retrofit dengan RxJava.
     */


    @POST("users/login")
    @FormUrlEncoded
    Call<AccountResponse> usersLogin(@Field("email") String email,
                                     @Field("password") String password);
    @POST("tasks/get-all-of-my-pt")
    Call<TaskLst> taskGetMine();

    @POST("chat/get-my-rooms")
    Call<RespJArr> getMyChatRoom();

    @FormUrlEncoded
    @POST("tasks/get-by-id")
    Call<RespJObj> taskGetByID(@Field("id") String id);

    @FormUrlEncoded
    @POST("chat/get-messages")
    Call<RespJArr> getMessagesByPtID(@Field("pt_id") String id);
}

