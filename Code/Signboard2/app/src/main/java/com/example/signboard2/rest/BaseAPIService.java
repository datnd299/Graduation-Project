package com.example.signboard2.rest;

import com.example.signboard2.model.account.AccountResponse;
import com.example.signboard2.model.task.TaskLst;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
}

