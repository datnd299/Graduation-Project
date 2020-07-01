package com.example.signboard2.ui.tasks.detail.setup;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Response;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.signboard2.MainActivity;
import com.example.signboard2.R;
import com.example.signboard2.model.RespJObj;
import com.example.signboard2.model.other.ImageText;
import com.example.signboard2.model.other.LocationCollected;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.ImageSignboardReport;
import com.example.signboard2.model.task.Task;
import com.example.signboard2.model.task.report.SetupTaskReportObj;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.ui.image.SteganoActivity;
import com.example.signboard2.ui.tasks.components.ListImageTextAdapter;
import com.example.signboard2.utils.Utils;
import com.example.signboard2.utils.imagesteganographylibrary.Text.AsyncTaskCallback.TextEncodingCallback;
import com.example.signboard2.utils.imagesteganographylibrary.Text.ImageSteganography;
import com.example.signboard2.utils.imagesteganographylibrary.Text.TextEncoding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SetupTaskDetail extends Fragment implements TextEncodingCallback {

    private SetupTaskDetailViewModel mViewModel;
    private  JsonObject currentTask;
    private RecyclerView recyclerView;
    BaseAPIService mApiService;
    Uri imageUri;
    ListOfListImageReportAdapter imagesSignboardReportListAdapter;
    List<ImageSignboardReport> imagesSignboardReportList;
    private JsonObject taskDetail;
    public SetupTaskDetail(JsonObject task) {
        super();
        this.currentTask = task;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public static SetupTaskDetail newInstance() {
        return new SetupTaskDetail(null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mApiService = UtilsAPI.getAPIService();
        View root = inflater.inflate(R.layout.setup_task_detail_fragment, container, false);
        Call<RespJObj> call = mApiService.taskGetByID(this.currentTask.get("_id").getAsString());
        SetupTaskDetail sef = this;
        ((MainActivity) getActivity()).showPrg();
        call.enqueue(new ApiCallback<RespJObj>(getActivity()) {
            @Override
            public void handleSuccess(Call<RespJObj> call, Response<RespJObj> response) {
                ((MainActivity) getActivity()).hidePrg();
                taskDetail = response.body().getData();
                ((TextView) root.findViewById(R.id.txtPlaceName)).setText("Điểm treo: "+taskDetail.getAsJsonObject("setup_task").getAsJsonObject("place_rental").get("name").getAsString());
                ((TextView) root.findViewById(R.id.txtPlaceAddress)).setText("Địa chỉ: "+taskDetail.getAsJsonObject("setup_task").getAsJsonObject("place_rental").get("address").getAsString());
                JsonArray accs = taskDetail.getAsJsonObject("setup_task").getAsJsonArray("accs");
                String accStr="";
                for (JsonElement acc :accs){
                    JsonObject jAcc = acc.getAsJsonObject();
                    accStr+=jAcc.get("name").getAsString()+", ";
                }
                if(accStr.length()>0){
                    accStr = accStr.substring(0,accStr.length()-2);

                }
                ((TextView) root.findViewById(R.id.txtEm)).setText("Người thực hiện: "+accStr);
                String start = Utils.toDateTimeString(taskDetail.get("start").getAsString()) ;
                String end = Utils.toDateTimeString(taskDetail.get("end").getAsString()) ;
                ((TextView) root.findViewById(R.id.txtTime)).setText("Thực hiện: "+start+" -> "+end);

                recyclerView =  (RecyclerView) root.findViewById(R.id.mRecyclerViewSignboard);


                ArrayList<ImageText> imgLst = new ArrayList<ImageText>();
                imagesSignboardReportList = new ArrayList<ImageSignboardReport>();
                JsonArray sbs = taskDetail.getAsJsonObject("setup_task").getAsJsonArray("signboards");



                for (JsonElement sb :sbs){
                    JsonObject jSb = sb.getAsJsonObject();
                    JsonArray imgs = jSb.getAsJsonArray("imgs");
                    String img = imgs.get(0).getAsString();
                    ImageText imgT = new ImageText(UtilsAPI.BASE_URL_API+"file/get/"+img,jSb.get("name").getAsString());
                    imgLst.add(imgT);
                    ImageSignboardReport imageSignboardReport=new ImageSignboardReport();
                    imageSignboardReport.setName(jSb.get("name").getAsString());
                    imageSignboardReport.setId(jSb.get("_id").getAsString());
                    imageSignboardReport.setImgs(new ArrayList<ImageReport>());

                    imagesSignboardReportList.add(imageSignboardReport);
                }
                if(taskDetail.getAsJsonObject("setup_task_report")!=null){
                    JsonArray sbsReported = taskDetail.getAsJsonObject("setup_task_report").getAsJsonArray("signboards");
                    for (JsonElement sb :sbsReported){
                        JsonObject jSb = sb.getAsJsonObject();
                        for(ImageSignboardReport imageSignboardReport:imagesSignboardReportList){
                            if(jSb.getAsJsonObject("s_id").get("_id").getAsString().equals(imageSignboardReport.getId())){
                                imageSignboardReport.setImgs(new ArrayList<ImageReport>());
                                JsonArray jImgsReportedArr = jSb.getAsJsonArray("imgs");
                                for (JsonElement im :jImgsReportedArr){
                                   JsonObject  jImg = im.getAsJsonObject();
                                   ImageReport imageReport = new ImageReport();
                                   imageReport.setHaveImg(true);
                                   imageReport.setUploadedID(jImg.get("_id").getAsString());
                                    imageReport.setUploadedName(jImg.get("name").getAsString());
                                    imageSignboardReport.getImgs().add(imageReport);
                                }
                            }
                        }


                    }
                }else {

                }


                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(),LinearLayoutManager.HORIZONTAL,false));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                ListImageTextAdapter adapter = new ListImageTextAdapter(root.getContext(),recyclerView,sef, imgLst);




                ListView lv = ((ListView) root.findViewById(R.id.listRecyclerView));
                ViewGroup.LayoutParams params = lv.getLayoutParams();
                params.height = 435*2;
                lv.setLayoutParams(params);
                lv.requestLayout();
                imagesSignboardReportListAdapter= new ListOfListImageReportAdapter(root.getContext(), sef, imagesSignboardReportList);
                lv.setAdapter(imagesSignboardReportListAdapter);
                recyclerView.setAdapter(adapter);



                if(taskDetail.getAsJsonObject("setup_task_report")!=null){
                    JsonObject newLatLng = taskDetail.getAsJsonObject("setup_task_report").getAsJsonObject("new_lat_lng");
                    ((TextView) root.findViewById(R.id.edtLat)).setText(newLatLng.get("lat").getAsString());
                    ((TextView) root.findViewById(R.id.edtLng)).setText(newLatLng.get("lng").getAsString());
                    if(taskDetail.getAsJsonObject("setup_task_report").get("note")!=null){
                        ((TextView) root.findViewById(R.id.edtNote)).setText(taskDetail.getAsJsonObject("setup_task_report").get("note").getAsString());
                    }

                }
            }
            @Override
            public void handleFailure(Call<RespJObj> call, Response<RespJObj> response, Throwable t) {
                ((MainActivity) getActivity()).hidePrg();
            }
        });


        TextInputEditText edtLat =(TextInputEditText) root.findViewById(R.id.edtLat);
        TextInputEditText edtLng =(TextInputEditText) root.findViewById(R.id.edtLng);
        ((Button)root.findViewById(R.id.btnSetLocation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               LocationCollected locationCollected = ((MainActivity) getActivity()).getLocationCollected();
               if(locationCollected.isTrusted()){
                   edtLat.setText(Double.toString(locationCollected.getLocation().getLatitude()));
                   edtLng.setText(Double.toString(locationCollected.getLocation().getLongitude()));
               }else {
                   edtLat.setText("0");
                   edtLng.setText("0");
               }
            }
        });
        ((Button)root.findViewById(R.id.btnReport)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allowReport = true;
                for (ImageSignboardReport imagesSignboardReport:imagesSignboardReportList){
                    for(ImageReport img:imagesSignboardReport.getImgs()){
                        if(img.getHaveImg()&&img.getUploadedID()==null){
                            img.upload();
                            allowReport = false;
                        }
                    }
                }

                if(!allowReport) return;
                ((MainActivity) getActivity()).showPrg();
                String id = taskDetail.get("_id").getAsString();
                String note = ((TextInputEditText) root.findViewById(R.id.edtNote)).getText().toString();
                Double newLat = Double.parseDouble (((TextInputEditText) root.findViewById(R.id.edtLat)).getText().toString());
                Double newLng = Double.parseDouble (((TextInputEditText) root.findViewById(R.id.edtLng)).getText().toString());
                SetupTaskReportObj obj = new SetupTaskReportObj(id,newLat,newLng,note,imagesSignboardReportList);


                obj.setId(taskDetail.get("_id").getAsString());


                Call<RespJObj> call =  mApiService.reportTask(obj);
                ((MainActivity) getActivity()).showPrg();
                call.enqueue(new ApiCallback<RespJObj>(getActivity()) {

                    @Override
                    public void handleSuccess(Call<RespJObj> call, Response<RespJObj> response) {

                        ((MainActivity) getActivity()).hidePrg();

                        Log.d("xxx", "handleSuccess: "+response.body().getMessage());
                    }

                    @Override
                    public void handleFailure(Call<RespJObj> call, Response<RespJObj> response, Throwable t) {
                        Log.d("xxx", "handleSuccess: "+response.message());
                    }
                });



            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (resultCode == -1) {
                Bitmap bp2 = null;
                String imagePath = "";
                try {
                    imagePath = ((MainActivity) getActivity()).getRealPathFromURI(imageUri);
                    bp2 = BitmapFactory.decodeFile(imagePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ImageReport imageReport = null;
                for (ImageSignboardReport imagesSignboardReport:imagesSignboardReportList){
                    for(ImageReport img:imagesSignboardReport.getImgs()){
                        if(img.getTakingPhoto()){
                            imageReport = img;
                            img.setImg(bp2);
                            img.setTakingPhoto(false);
                            img.setHaveImg(true);
                            ImageReport newImg = new ImageReport();
                            newImg.setListImageAdapter(img.getListImageAdapter());
                            imagesSignboardReport.getImgs().add(newImg);

                            imagesSignboardReport.getListImageAdapter().notifyDataSetChanged();
                            imagesSignboardReportListAdapter.notifyDataSetChanged();

                        }
                    }
                }
                ((MainActivity)getActivity()).encodeStegano(bp2,imageReport);
            }
            else if (resultCode == 0) {

            } else {

            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SetupTaskDetailViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStartTextEncoding() {

    }

    @Override
    public void onCompleteTextEncoding(ImageSteganography result) {

    }
}
