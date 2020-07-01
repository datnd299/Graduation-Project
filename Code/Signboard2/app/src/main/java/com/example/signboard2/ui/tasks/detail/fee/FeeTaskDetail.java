package com.example.signboard2.ui.tasks.detail.fee;

import androidx.lifecycle.ViewModelProviders;

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
import com.example.signboard2.model.task.FeeLineReport;
import com.example.signboard2.model.task.Task;
import com.example.signboard2.model.task.report.FeeTaskReportObj;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class FeeTaskDetail extends Fragment {

    private FeeTaskDetailViewModel mViewModel;
    private  JsonObject currentTask;
    private RecyclerView recyclerView;
    BaseAPIService mApiService;
    List<FeeLineReport> fLst;
    private JsonObject taskDetail;

    public FeeTaskDetail(JsonObject task) {
        this.currentTask = task;
    }

    public static FeeTaskDetail newInstance() {
        return new FeeTaskDetail(null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mApiService = UtilsAPI.getAPIService();



        View root = inflater.inflate(R.layout.fee_task_detail_fragment, container, false);
        Call<RespJObj> call = mApiService.taskGetByID(this.currentTask.get("_id").getAsString());
        FeeTaskDetail sef = this;
        ((MainActivity) getActivity()).showPrg();
        call.enqueue(new ApiCallback<RespJObj>(getActivity()) {
            @Override
            public void handleSuccess(Call<RespJObj> call, Response<RespJObj> response) {
                ((MainActivity) getActivity()).hidePrg();
                taskDetail = response.body().getData();
                JsonArray accs = taskDetail.getAsJsonObject("fee_task").getAsJsonArray("accs");
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

                 fLst = new ArrayList<FeeLineReport>();
                JsonArray jLFee = taskDetail.getAsJsonObject("fee_task").getAsJsonArray("fee_detail");



                for (JsonElement f :jLFee){
                    JsonObject jT = f.getAsJsonObject();
                    JsonObject fPlace = jT.getAsJsonObject("place_rental");
                    PlaceRental pl = new PlaceRental();
                    pl.setName(fPlace.get("name").getAsString());
                    pl.setId(fPlace.get("_id").getAsString());
                    pl.setTimeUnit(fPlace.get("time_unit").getAsString());
                    FeeLineReport fLineReport = new FeeLineReport();
                    fLineReport.setTimes(jT.getAsJsonArray("times"));
                    fLineReport.setPlace(pl);
                    fLineReport.setRole(taskDetail.get("role").getAsString());
                    fLineReport.setAmount(jT.get("amount").getAsDouble());
                    fLineReport.setEmNote(jT.getAsJsonObject("report").get("em_note").getAsString());
                    fLineReport.setPtBNote(jT.getAsJsonObject("report").get("pt_note").getAsString());
                    fLineReport.setEmConfirm(jT.getAsJsonObject("report").get("em_confirm").getAsBoolean());
                    fLineReport.setPtBConfirm(jT.getAsJsonObject("report").get("pt_confirm").getAsBoolean());
//                    fLineReport.setEmStatus(1);
//                    fLineReport.setPtBStatus(1);
                    fLst.add(fLineReport);
                }

                ListFeeAdapter adapter = new ListFeeAdapter(root.getContext(),sef, fLst);

                ListView lv = ((ListView) root.findViewById(R.id.list_fee));
//                ViewGroup.LayoutParams params = lv.getLayoutParams();
//                params.height = 435*2;
//                lv.setLayoutParams(params);
//                lv.requestLayout();




                lv.setAdapter(adapter);
//                int contentHeight = lv.getChildAt(0).getHeight();

                // set listview height
                ViewGroup.LayoutParams lp = lv.getLayoutParams();
                lp.height = 800*fLst.size();
                lv.setLayoutParams(lp);

            }
            @Override
            public void handleFailure(Call<RespJObj> call, Response<RespJObj> response, Throwable t) {
                ((MainActivity) getActivity()).hidePrg();
            }
        });


//        Call<RespJObj> call2 = mApiService.taskGetByID(this.currentTask.get("_id").getAsString());
        ((Button)root.findViewById(R.id.btnReport)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeeTaskReportObj obj = new FeeTaskReportObj(currentTask.get("_id").getAsString(),fLst);
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
                        ((MainActivity) getActivity()).hidePrg();
                    }
                });

            }
        });
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FeeTaskDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
