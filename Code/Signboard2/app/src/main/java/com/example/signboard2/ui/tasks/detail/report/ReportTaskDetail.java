package com.example.signboard2.ui.tasks.detail.report;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.signboard2.MainActivity;
import com.example.signboard2.R;
import com.example.signboard2.model.RespJObj;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.ImageSignboardReport;
import com.example.signboard2.model.task.report.ReportTaskReportObj;
import com.example.signboard2.model.task.report.SetupTaskReportObj;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.ui.tasks.components.ListImageAdapter;
import com.example.signboard2.ui.tasks.detail.check.ListCheckSignboardAdapter;
import com.example.signboard2.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ReportTaskDetail extends Fragment {

    private TaskDetailViewModel mViewModel;
    private JsonObject currentTask;
    private JsonObject taskDetail;
    private ArrayList<ImageReport> imgLst;
    RecyclerView recyclerView;
    List<ImageSignboardReport> signboardReportLst;
    ListCheckSignboardAdapter listCheckSignboardAdapter;
    Uri imageUri;
    BaseAPIService mApiService;
    public ReportTaskDetail(JsonObject task) {
        super();
        this.currentTask = task;
    }
    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
    public static ReportTaskDetail newInstance() {
        return new ReportTaskDetail(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.report_task_detail_fragment, container, false);
        mApiService = UtilsAPI.getAPIService();
        Call<RespJObj> call = mApiService.taskGetByID(this.currentTask.get("_id").getAsString());
        ((Button)root.findViewById(R.id.btnReport)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allowReport = true;
                for (ImageSignboardReport imagesSignboardReport:signboardReportLst){
                    for(ImageReport img:imagesSignboardReport.getImgs()){
                        if(img.getHaveImg()&&img.getUploadedID()==null){
                            img.upload();
                            allowReport = false;
                        }
                    }
                }
//
                if(!allowReport) return;
                ((MainActivity) getActivity()).showPrg();
                String id = taskDetail.get("_id").getAsString();

                ReportTaskReportObj obj = new ReportTaskReportObj(id,signboardReportLst);



                Call<RespJObj> call =  mApiService.reportTask(obj);
                call.enqueue(new ApiCallback<RespJObj>(getActivity()) {

                    @Override
                    public void handleSuccess(Call<RespJObj> call, Response<RespJObj> response) {

                        ((MainActivity) getActivity()).hidePrg();

                        Log.d("xxx", "handleSuccess: "+response.body().getMessage());
                    }

                    @Override
                    public void handleFailure(Call<RespJObj> call, Response<RespJObj> response, Throwable t) {
                        ((MainActivity) getActivity()).hidePrg();
                        Log.d("xxx", "handleSuccess: "+response.message());
                    }
                });



            }
        });
        ((MainActivity) getActivity()).showPrg();
        call.enqueue(new ApiCallback<RespJObj>(getActivity()) {
            @Override
            public void handleSuccess(Call<RespJObj> call, Response<RespJObj> response) {
                ((MainActivity) getActivity()).hidePrg();
                taskDetail = response.body().getData();
                ((TextView) root.findViewById(R.id.txtPlaceName)).setText("Điểm treo: "+taskDetail.getAsJsonObject("report_task").getAsJsonObject("place_rental").get("name").getAsString());
                JsonArray jArr = taskDetail.getAsJsonObject("report_task").getAsJsonObject("place_rental").getAsJsonArray("signboards");
                String sbStr="";
                for (JsonElement jESb :jArr){
                    JsonObject jSb = jESb.getAsJsonObject();
                    sbStr+=jSb.get("name").getAsString()+", ";
                }
                if(sbStr.length()>0){
                    sbStr = sbStr.substring(0,sbStr.length()-2);

                }

                String start = Utils.toDateTimeString(taskDetail.get("start").getAsString()) ;
                String end = Utils.toDateTimeString(taskDetail.get("end").getAsString()) ;
                ((TextView) root.findViewById(R.id.txtTime)).setText("Thực hiện: "+start+" -> "+end);

                if(taskDetail.getAsJsonObject("report_task").get("repeat_type").getAsString().equals("repeat")){
                    ((TextView) root.findViewById(R.id.txtRepeatType)).setText("Loại: Lặp lại");
                }else {
                    ((TextView) root.findViewById(R.id.txtRepeatType)).setText("Loại: Một lần");
                }
                ((TextView) root.findViewById(R.id.txtSignboardName)).setText("Biển quảng cáo: "+sbStr);

                ListView listView = (ListView) root.findViewById(R.id.listSb);

                JsonArray jArSbs = taskDetail.getAsJsonObject("report_task").getAsJsonObject("place_rental").getAsJsonArray("signboards");
                signboardReportLst = new ArrayList<>();
                listCheckSignboardAdapter = new ListCheckSignboardAdapter(root.getContext(),ReportTaskDetail.this,signboardReportLst);
                for (JsonElement jESb:jArSbs){
                    JsonObject jOSb = jESb.getAsJsonObject();
                    ImageSignboardReport  imageSignboardReport= new ImageSignboardReport();
                    imageSignboardReport.setId(jOSb.get("_id").getAsString());
                    imageSignboardReport.setName(jOSb.get("name").getAsString());
                    List<ImageReport> imageReportLst = new ArrayList<>();
                    ImageReport imageReport = new ImageReport();
                    imageReportLst.add(imageReport);
                    imageSignboardReport.setImgs(imageReportLst);
                    signboardReportLst.add(imageSignboardReport);
                }

                if(taskDetail.getAsJsonObject("report_task_report")!=null){
                    jArSbs = taskDetail.getAsJsonObject("report_task_report").getAsJsonArray("signboards");
                    for (JsonElement jESb:jArSbs) {
                        JsonObject jOSb = jESb.getAsJsonObject();
                        for (ImageSignboardReport imageSignboardReport:signboardReportLst){
                            if(jOSb.getAsJsonObject("s_id").get("_id").getAsString().equals(imageSignboardReport.getId())){
                                imageSignboardReport.setNote(jOSb.get("note").getAsString());
                                imageSignboardReport.setRating(jOSb.get("rating").getAsFloat());

                                JsonArray jArImgs = jOSb.getAsJsonArray("imgs");
                                for (JsonElement jEImg:jArImgs) {
                                    ImageReport imageReport = new ImageReport();
                                    imageReport.setHaveImg(true);
                                    imageReport.setUploadedID(jEImg.getAsJsonObject().get("_id").getAsString());
                                    imageReport.setUploadedName(jEImg.getAsJsonObject().get("name").getAsString());
                                    imageSignboardReport.getImgs().add(0,imageReport);
                                }
                            }
                        }
                    }
                }


                listView.setAdapter(listCheckSignboardAdapter);
            }

            @Override
            public void handleFailure(Call<RespJObj> call, Response<RespJObj> response, Throwable t) {
                ((MainActivity) getActivity()).hidePrg();
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
                for (ImageSignboardReport imagesSignboardReport:signboardReportLst){
                    for(ImageReport img:imagesSignboardReport.getImgs()){
                        if(img.getTakingPhoto()){
                            imageReport = img;
                            img.setImg(bp2);
                            img.setTakingPhoto(false);
                            img.setHaveImg(true);
                            ImageReport newImg = new ImageReport();
                            newImg.setListImageAdapter(img.getListImageAdapter());
                            imagesSignboardReport.getImgs().add(newImg);

                        }
                    }
                }
                listCheckSignboardAdapter.refreshRecycleView();
//                listCheckSignboardAdapter.refreshRecycleView(); //hàm này gọi NotnotifyDataSetChanged();
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
        mViewModel = ViewModelProviders.of(this).get(TaskDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
