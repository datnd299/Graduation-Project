package com.example.signboard2.model.task;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.example.signboard2.model.RespJObj;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.ui.tasks.components.ListImageAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageReport {
    BaseAPIService mApiService;
    private ListImageAdapter listImageAdapter;
    private Bitmap img;
    private Boolean uploading;
    private Boolean takingPhoto;
    private String uploadedID;
    private File savedFile;
    private String uploadedName;

    public String getUploadedID() {
        return uploadedID;
    }

    public void setUploadedID(String url) {
        this.uploadedID = url;
    }

    public File getSavedFile() {
        return savedFile;
    }

    public void setSavedFile(File savedFile) {
        this.savedFile = savedFile;
    }

    public ListImageAdapter getListImageAdapter() {
        return listImageAdapter;
    }

    public void setListImageAdapter(ListImageAdapter listImageAdapter) {
        this.listImageAdapter = listImageAdapter;
    }

    public String getUploadedName() {
        return uploadedName;
    }

    public void setUploadedName(String uploadedName) {
        this.uploadedName = uploadedName;
    }

    public Bitmap getImg() {
        return img;
    }

    public Boolean getTakingPhoto() {
        return takingPhoto;
    }

    public void setTakingPhoto(Boolean takingPhoto) {
        this.takingPhoto = takingPhoto;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Boolean getUploading() {
        return uploading;
    }

    public void setUploading(Boolean uploading) {
        this.uploading = uploading;
    }

    public Boolean getHaveImg() {
        return haveImg;
    }

    public void setHaveImg(Boolean haveImg) {
        this.haveImg = haveImg;
    }

    private Boolean haveImg;

    public ImageReport(){
        this.mApiService = UtilsAPI.getAPIService();
        this.img = null;
        this.haveImg = false;
        this.uploading = false;
        this.takingPhoto = false;
    }


    public void upload() {


        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), savedFile);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", savedFile.getName(), requestFile);
        Call<RespJObj> call = mApiService.uploadImage(body);

       
        this.uploading = true;

        listImageAdapter.notifyDataSetChanged();
        call.enqueue(new ApiCallback<RespJObj>(null) {
            @Override
            public void handleSuccess(Call<RespJObj> call, Response<RespJObj> response) {
                uploading  = false;
                listImageAdapter.notifyDataSetChanged();
                haveImg = true;
                takingPhoto = false;
                uploadedID=response.body().getData().getAsJsonObject("uploaded_file").get("_id").getAsString();
                uploadedName=response.body().getData().getAsJsonObject("uploaded_file").get("name").getAsString();

                Log.d("xxxx", "handleSuccess: ");
            }

            @Override
            public void handleFailure(Call<RespJObj> call, Response<RespJObj> response, Throwable t) {
                uploading = false;
                Log.d("xxxx", "handleFailure: ");
            }



        });
    }


}
