package com.example.signboard2.ui.tasks.detail.check;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.signboard2.model.place.PlaceRental;
import com.example.signboard2.model.task.CheckLineReport;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.ImageSignboardReport;
import com.example.signboard2.model.task.Task;
import com.example.signboard2.model.task.report.CheckTaskReportObj;
import com.example.signboard2.model.task.report.SetupTaskReportObj;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class CheckTaskDetail extends Fragment {

    private CheckTaskDetailViewModel mViewModel;
    private  JsonObject currentTask;
    private RecyclerView recyclerView;
    BaseAPIService mApiService;
    List<CheckLineReport> cLst;
    private JsonObject taskDetail;
    public CheckTaskDetail(JsonObject task) {
        this.currentTask = task;

    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    Uri imageUri;
    public static CheckTaskDetail newInstance() {
        return new CheckTaskDetail(null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mApiService = UtilsAPI.getAPIService();



        View root = inflater.inflate(R.layout.check_task_detail_fragment, container, false);
        Call<RespJObj> call = mApiService.taskGetByID(this.currentTask.get("_id").getAsString());
        CheckTaskDetail sef = this;
        ((MainActivity) getActivity()).showPrg();
        call.enqueue(new ApiCallback<RespJObj>(getActivity()) {
            @Override
            public void handleSuccess(Call<RespJObj> call, Response<RespJObj> response) {
                ((MainActivity) getActivity()).hidePrg();
                taskDetail = response.body().getData();
                cLst = new ArrayList<CheckLineReport>();

                JsonArray accs = taskDetail.getAsJsonObject("check_task").getAsJsonArray("accs");
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
                ((TextView) root.findViewById(R.id.txtTime)).setText("Thời gian: "+start+" -> "+end);



                JsonArray jLCheck = taskDetail.getAsJsonObject("check_task").getAsJsonArray("place_rental");
                for (JsonElement f :jLCheck){
                    JsonObject cPlace = f.getAsJsonObject();
                    PlaceRental pl = new PlaceRental();
                    pl.setName(cPlace.get("name").getAsString());
                    pl.setId(cPlace.get("_id").getAsString());
                    CheckLineReport cLineReport = new CheckLineReport();
                    JsonArray jArSbs = cPlace.getAsJsonArray("signboards");
                    List<ImageSignboardReport> imageSignboardReportList = new ArrayList<ImageSignboardReport>();
                    for (JsonElement jeSb :jArSbs){
                        JsonObject jSb = jeSb.getAsJsonObject();
                        ImageSignboardReport imageSignboardReport = new ImageSignboardReport();
                        imageSignboardReport.setId(jSb.get("_id").getAsString());
                        imageSignboardReport.setName(jSb.get("name").getAsString());
                        imageSignboardReport.setImgs(new ArrayList<>());
                        imageSignboardReportList.add(imageSignboardReport);
                        ImageReport imageReport = new ImageReport();
                        imageSignboardReport.getImgs().add(imageReport);
                    }
                    cLineReport.setPlace(pl);
                    cLineReport.setImgsSignboardReportList(imageSignboardReportList);
                    cLineReport.setStatus(1);

                    cLst.add(cLineReport);
                }

                if(taskDetail.getAsJsonObject("check_task_report")!=null){
                    JsonArray jArrReport = taskDetail.getAsJsonObject("check_task_report").getAsJsonArray("place_rental");
                    for (JsonElement f :jArrReport){
                        JsonObject jPl = f.getAsJsonObject();
                        for(CheckLineReport linePl:cLst){
                            if(linePl.getPlace().getId().equals(jPl.getAsJsonObject("pl_id").get("_id").getAsString())){
                                JsonArray jArSb = jPl.getAsJsonArray("signboards");
                                for (JsonElement jESb :jArSb){
                                    JsonObject jSb = jESb.getAsJsonObject();
                                    for (ImageSignboardReport sb:linePl.getImgsSignboardReportList()){
                                        if(sb.getId().equals(jSb.getAsJsonObject("s_id").get("_id").getAsString())){
                                            sb.setRating(jSb.get("rating").getAsFloat());
                                            sb.setNote(jSb.get("note").getAsString());

                                            JsonArray jArImgs = jSb.getAsJsonArray("imgs");
                                            for (JsonElement jEImg: jArImgs){
                                                JsonObject jImg = jEImg.getAsJsonObject();
                                                ImageReport imageReport = new ImageReport();
                                                imageReport.setUploadedName(jImg.get("name").getAsString());
                                                imageReport.setUploadedID(jImg.get("_id").getAsString());
                                                imageReport.setHaveImg(true);
                                                sb.getImgs().add(0,imageReport);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                ListCheckAdapter adapter = new ListCheckAdapter(root.getContext(),sef, cLst);

                ListView lv = ((ListView) root.findViewById(R.id.list_check));
                ViewGroup.LayoutParams params = lv.getLayoutParams();
                params.height = 775*adapter.getCount();
                lv.setLayoutParams(params);
                lv.requestLayout();

//                List<List<ImageReport>> lt = new ArrayList<>();
//                for (int i =0;i<2;i++){
//                    lt.add(new ArrayList<ImageReport>());
//                }
                lv.setAdapter(adapter);


            }
            @Override
            public void handleFailure(Call<RespJObj> call, Response<RespJObj> response, Throwable t) {
                ((MainActivity) getActivity()).hidePrg();
            }
        });


        ((Button)root.findViewById(R.id.btnReport)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allowReport = true;
                for(CheckLineReport cLine: cLst){
                    for (ImageSignboardReport imagesSignboardReport:cLine.getImgsSignboardReportList()){
                        for(ImageReport img:imagesSignboardReport.getImgs()){
                            if(img.getHaveImg()&&img.getUploadedID()==null){
                                img.upload();
                                allowReport = false;
                            }
                        }
                    }
                }


                if(!allowReport) return;
                String id = taskDetail.get("_id").getAsString();
                CheckTaskReportObj obj = new CheckTaskReportObj(id,cLst,"");
                Call<RespJObj> call =  mApiService.reportTask(obj);
                ((MainActivity) getActivity()).showPrg();
                call.enqueue(new ApiCallback<RespJObj>(getActivity()) {

                    @Override
                    public void handleSuccess(Call<RespJObj> call, Response<RespJObj> response) {
                        ((MainActivity) getActivity()).hidePrg();
                        ((MainActivity) getActivity()).hidePrg();

//                        Log.d("xxx", "handleSuccess: "+response.body().getMessage());
                    }

                    @Override
                    public void handleFailure(Call<RespJObj> call, Response<RespJObj> response, Throwable t) {
                        ((MainActivity) getActivity()).hidePrg();
//                        Log.d("xxx", "handleSuccess: "+response.message());
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
                for (CheckLineReport check:cLst) {

                    for (ImageSignboardReport imagesSignboardReport : check.getImgsSignboardReportList()) {
                        for (ImageReport img : imagesSignboardReport.getImgs()) {
                            if (img.getTakingPhoto()) {
                                imageReport = img;
                                img.setImg(bp2);
                                img.setTakingPhoto(false);
                                img.setHaveImg(true);

                                ImageReport newImg = new ImageReport();
                                newImg.setListImageAdapter(img.getListImageAdapter());
//                               imagesSignboardReport.getImgs().add(newImg);

                                imagesSignboardReport.getListImageAdapter().notifyDataSetChanged();
                                img.getListImageAdapter().notifyDataSetChanged();
//                                imagesSignboardReportListAdapter.notifyDataSetChanged();

                            }
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
        mViewModel = ViewModelProviders.of(this).get(CheckTaskDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
